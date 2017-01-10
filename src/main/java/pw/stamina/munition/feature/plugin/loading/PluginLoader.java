package pw.stamina.munition.feature.plugin.loading;

import pw.stamina.munition.core.ExtensionDescriptor;
import pw.stamina.munition.feature.plugin.Plugin;
import pw.stamina.munition.feature.plugin.loading.configuration.PluginConfigurationDescriptor;
import pw.stamina.munition.feature.plugin.loading.configuration.loading.PluginConfigurationLoader;
import pw.stamina.munition.feature.plugin.loading.configuration.parsing.PluginConfigurationParser;
import pw.stamina.munition.feature.plugin.loading.includes.IncludeResolver;

import java.net.URL;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author Mark Johnson
 */
public class PluginLoader implements Iterable<Plugin> {

    private final ExtensionDescriptor extensionDescriptor;

    private final IncludeResolver<Plugin> includeResolver;

    private final String configurationFilePath;

    private final PluginConfigurationLoader configurationLoader;

    private final PluginConfigurationParser configurationParser;

    private final List<Plugin> cachedPlugins;

    private Iterator<Plugin> lazyLookupIterator;

    public PluginLoader(final ExtensionDescriptor extensionDescriptor,
                        final IncludeResolver<Plugin> includeResolver,
                        final String configurationFilePath,
                        final PluginConfigurationLoader configurationLoader,
                        final PluginConfigurationParser configurationParser) {
        this.extensionDescriptor = extensionDescriptor;
        this.includeResolver = includeResolver;
        this.configurationFilePath = configurationFilePath;
        this.configurationLoader = configurationLoader;
        this.configurationParser = configurationParser;
        this.cachedPlugins  = new ArrayList<>();
        reload();
    }

    public void reload() {
        cachedPlugins.clear();
        lazyLookupIterator = new StrictlyConsistentIteratorDecorator<>(
                new PrivilegedAccessIteratorDecorator<>(
                        new LazyPluginIterator()),
                () -> new NoSuchElementException("There are no more plugins to load!"));
    }

    @Override
    public Iterator<Plugin> iterator() {
        return new CachedPluginIterator();
    }

    private class CachedPluginIterator implements Iterator<Plugin> {

        private final Iterator<Plugin> knownPlugins = cachedPlugins.iterator();

        public boolean hasNext() {
            return knownPlugins.hasNext() || lazyLookupIterator.hasNext();
        }

        public Plugin next() {
            if (knownPlugins.hasNext()) {
                return knownPlugins.next();
            } else {
                return lazyLookupIterator.next();
            }
        }
    }

    private class LazyPluginIterator implements Iterator<Plugin> {

        private Iterator<URL> configurationFiles;

        private PluginConfigurationDescriptor pendingConfigurationDescriptor;

        private Iterator<String> pendingPluginClasses;

        private String nextPluginClass;

        @Override
        public boolean hasNext() {
            if (nextPluginClass != null) {
                return true;
            }
            if (configurationFiles == null) {
                configurationFiles = configurationLoader.loadConfigurations(configurationFilePath);
            }
            if (!ensureMorePluginClassesAreAvailable()) {
                return false;
            } else {
                nextPluginClass = pendingPluginClasses.next();
                return true;
            }
        }

        private boolean ensureMorePluginClassesAreAvailable() {
            while (pendingPluginClasses == null || !pendingPluginClasses.hasNext()) {
                if (!configurationFiles.hasNext()) {
                    return false;
                }
                pendingConfigurationDescriptor = configurationParser.parsePluginConfiguration(configurationFiles.next());
                if (!pendingConfigurationDescriptor.getPluginTarget().isCompatibleWith(extensionDescriptor)) {
                    throw new PluginLoadingException("Incompatible plugin targets: Found " + extensionDescriptor +
                            " but requires " + pendingConfigurationDescriptor.getPluginTarget());
                }
                pendingPluginClasses = pendingConfigurationDescriptor.getPluginIncludes().getIncludeData().iterator();
            }
            return true;
        }

        @Override
        public Plugin next() {
            final String pluginClassName = getAndUpdateNextPluginClassName();

            final Plugin plugin = includeResolver.resolveInclude(pluginClassName);

            cachedPlugins.add(plugin);

            return plugin;
        }

        private String getAndUpdateNextPluginClassName() {
            final String pluginClassName = nextPluginClass;
            nextPluginClass = null;
            return pluginClassName;
        }
    }

    private static class PrivilegedAccessIteratorDecorator<T> implements Iterator<T> {

        private final Iterator<T> backingIterator;

        private final AccessControlContext accessController;

        PrivilegedAccessIteratorDecorator(final Iterator<T> backingIterator) {
            this.backingIterator = backingIterator;
            this.accessController = (System.getSecurityManager() != null) ? AccessController.getContext() : null;
        }

        @Override
        public boolean hasNext() {
            if (accessController == null) {
                return backingIterator.hasNext();
            } else {
                return AccessController.doPrivileged((PrivilegedAction<Boolean>) backingIterator::hasNext, accessController);
            }
        }

        @Override
        public T next() {
            if (accessController == null) {
                return backingIterator.next();
            } else {
                return AccessController.doPrivileged((PrivilegedAction<T>) backingIterator::next, accessController);
            }
        }
    }

    private static class StrictlyConsistentIteratorDecorator<T> implements Iterator<T> {

        private final Iterator<T> backingIterator;

        private final Supplier<? extends RuntimeException> exceptionSupplier;

        StrictlyConsistentIteratorDecorator(final Iterator<T> backingIterator, final Supplier<? extends RuntimeException> exceptionSupplier) {
            this.backingIterator = backingIterator;
            this.exceptionSupplier = exceptionSupplier;
        }

        @Override
        public boolean hasNext() {
            return backingIterator.hasNext();
        }

        @Override
        public T next() {
            if (backingIterator.hasNext()) {
                return backingIterator.next();
            } else {
                throw exceptionSupplier.get();
            }
        }
    }
}
