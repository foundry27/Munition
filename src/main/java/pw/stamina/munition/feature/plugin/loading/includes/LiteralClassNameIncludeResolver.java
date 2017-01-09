package pw.stamina.munition.feature.plugin.loading.includes;

import pw.stamina.munition.feature.plugin.loading.PluginLoadingException;
import pw.stamina.munition.feature.plugin.loading.includes.instantiation.InstantiationStrategy;
import pw.stamina.munition.feature.plugin.loading.includes.instantiation.ObjectInstantiationException;

import java.util.Objects;

/**
 * @author Mark Johnson
 */
public class LiteralClassNameIncludeResolver<T> implements IncludeResolver<T> {

    private final Class<T> includeType;

    private final ClassLoader classLoader;

    private final InstantiationStrategy<T> instantiationStrategy;

    public LiteralClassNameIncludeResolver(final Class<T> includeType, final ClassLoader classLoader, final InstantiationStrategy<T> instantiationStrategy) {
        this.includeType = Objects.requireNonNull(includeType, "The include type cannot be null");
        this.classLoader = Objects.requireNonNull(classLoader, "The ClassLoader cannot be null");
        this.instantiationStrategy = Objects.requireNonNull(instantiationStrategy, "The instantiation strategy cannot be null");
    }

    @Override
    public T resolveInclude(final String include) throws PluginIncludeResolutionException {
        try {
            final Class<? extends T> pluginClassLookup = tryResolvingClassFromName(include);
            return instantiationStrategy.instantiate(pluginClassLookup);
        } catch (final ObjectInstantiationException e) {
            throw new PluginIncludeResolutionException("Exception instantiating class associated with plugin include:", e);
        } catch (final PluginLoadingException e) {
            throw new PluginIncludeResolutionException("Exception resolving class name associated with plugin include:", e);
        }
    }

    private Class<? extends T> tryResolvingClassFromName(final String pluginClass) throws PluginLoadingException {
        final Class<?> classLookup;
        try {
            classLookup = Class.forName(pluginClass, false, classLoader);
        } catch (final ClassNotFoundException e) {
            throw new PluginLoadingException("Malformed plugin configuration: class '" + pluginClass + "' does not exist");
        }
        if (!includeType.isAssignableFrom(classLookup)) {
            throw new PluginLoadingException("Class " + classLookup.getCanonicalName() + " is not a subtype of class " + includeType.getCanonicalName());
        }
        return classLookup.asSubclass(includeType);
    }
}
