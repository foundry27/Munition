package pw.stamina.munition.feature.plugin.loading.configuration.loading;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author Mark Johnson
 */
public class ClassLoaderResourcePluginConfigurationLoader implements PluginConfigurationLoader {

    private final ClassLoader classLoader;

    public ClassLoaderResourcePluginConfigurationLoader(final ClassLoader classLoader) {
        this.classLoader = Objects.requireNonNull(classLoader, "The ClassLoader cannot be null");
    }

    @Override
    public Iterator<URL> loadConfigurations(final String configurationFileName) throws ConfigurationLoadingException {
        try {
            final Enumeration<URL> configurationFilesEnumeration = classLoader.getResources(configurationFileName);
            return new EnumerationToIterator<>(configurationFilesEnumeration);
        } catch (final IOException e) {
            throw new ConfigurationLoadingException("Exception encountered while reading configuration files for plugins from classloader:", e);
        }
    }

    private static class EnumerationToIterator<T> implements Iterator<T> {

        private final Enumeration<T> enumeration;

        EnumerationToIterator(final Enumeration<T> enumeration) {
            this.enumeration = enumeration;
        }

        public boolean hasNext() {
            return enumeration.hasMoreElements();
        }

        public T next() {
            return enumeration.nextElement();
        }
    }
}
