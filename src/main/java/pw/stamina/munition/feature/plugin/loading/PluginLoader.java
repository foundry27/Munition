package pw.stamina.munition.feature.plugin.loading;

import pw.stamina.munition.core.ExtensionDescriptor;
import pw.stamina.munition.feature.plugin.Plugin;
import pw.stamina.munition.feature.plugin.loading.configuration.PluginConfigurationDescriptor;
import pw.stamina.munition.feature.plugin.loading.configuration.parsing.PluginConfigurationParser;
import pw.stamina.munition.feature.plugin.loading.instantiation.InstantiationStrategy;

import java.io.IOException;
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

    private final ClassLoader classLoader;

    private final InstantiationStrategy<Plugin> instantiationStrategy;

    private final String configurationFilePath;

    private final PluginConfigurationParser configurationParser;

    private final Map<String, Plugin> cachedPlugins;

    private Iterator<Plugin> lazyLookupIterator;

    public PluginLoader(final ExtensionDescriptor extensionDescriptor,
                        final ClassLoader classLoader,
                        final InstantiationStrategy<Plugin> instantiationStrategy,
                        final String configurationFilePath,
                        final PluginConfigurationParser configurationParser) {
        this.extensionDescriptor = extensionDescriptor;
        this.classLoader = classLoader;
        this.instantiationStrategy = instantiationStrategy;
        this.configurationFilePath = configurationFilePath;
        this.configurationParser = configurationParser;
        this.cachedPlugins  = new LinkedHashMap<>();
        reload();
    }

    public void reload() {
        cachedPlugins.clear();
        lazyLookupIterator = new StrictlyConsistentIterator<>(new PrivilegedAccessIteratorDecorator<>(new LazyPluginIterator()),
                () -> new NoSuchElementException("There are no more plugins to load!"));
    }

    @Override
    public Iterator<Plugin> iterator() {
        return new CachedPluginIterator();
    }

    private class CachedPluginIterator implements Iterator<Plugin> {

        private final Iterator<Plugin> knownPlugins = cachedPlugins.values().iterator();

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

        private Enumeration<URL> configurationFiles;

        private PluginConfigurationDescriptor pendingConfigurationDescriptor;

        private Iterator<String> pendingPluginClasses;

        private String nextPluginClass;

        @Override
        public boolean hasNext() {
            if (nextPluginClass != null) {
                return true;
            }
            if (configurationFiles == null) {
                tryLoadingConfigurationFiles(configurationFilePath);
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
                if (!configurationFiles.hasMoreElements()) {
                    return false;
                }
                pendingConfigurationDescriptor = configurationParser.parsePluginConfiguration(configurationFiles.nextElement());
                if (!pendingConfigurationDescriptor.getPluginTarget().isCompatibleWith(extensionDescriptor)) {
                    throw new PluginLoadingException("Incompatible plugin targets: Found " + extensionDescriptor +
                            " but requires " + pendingConfigurationDescriptor.getPluginTarget());
                }
                pendingPluginClasses = pendingConfigurationDescriptor.getPluginIncludes().getIncludedPluginClasses().iterator();
            }
            return true;
        }

        private void tryLoadingConfigurationFiles(final String configurationFileName) {
            try {
                configurationFiles = classLoader.getResources(configurationFileName);
            } catch (final IOException e) {
                throw new PluginLoadingException("Exception encountered while reading configuration files for plugins:", e);
            }
        }

        @Override
        public Plugin next() {
            final String pluginClassName = getAndUpdateNextPluginClassName();

            final Class<? extends Plugin> pluginClass = tryResolvingClassFromName(pluginClassName);

            final Plugin plugin = instantiationStrategy.instantiate(pluginClass);

            cachedPlugins.put(pluginClassName, plugin);

            return plugin;
        }

        private String getAndUpdateNextPluginClassName() {
            final String pluginClassName = nextPluginClass;
            nextPluginClass = null;
            return pluginClassName;
        }

        private Class<? extends Plugin> tryResolvingClassFromName(final String pluginClass) {
            final Class<?> classLookup;
            try {
                classLookup = Class.forName(pluginClass, false, classLoader);
            } catch (final ClassNotFoundException e) {
                throw new PluginLoadingException("Malformed plugin configuration: class '" + nextPluginClass + "' does not exist");
            }
            if (!Plugin.class.isAssignableFrom(classLookup)) {
                throw new PluginLoadingException("Class " + classLookup.getCanonicalName() + " is not a subtype of class " + Plugin.class.getCanonicalName());
            }
            return classLookup.asSubclass(Plugin.class);
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

    private static class StrictlyConsistentIterator<T> implements Iterator<T> {

        private final Iterator<T> backingIterator;

        private final Supplier<? extends RuntimeException> exceptionSupplier;

        StrictlyConsistentIterator(final Iterator<T> backingIterator, final Supplier<? extends RuntimeException> exceptionSupplier) {
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
