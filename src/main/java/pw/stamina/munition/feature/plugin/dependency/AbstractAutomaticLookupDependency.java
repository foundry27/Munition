package pw.stamina.munition.feature.plugin.dependency;

import pw.stamina.munition.feature.plugin.dependency.resolution.DependencyResolver;
import pw.stamina.munition.feature.plugin.dependency.resolution.ResolutionObjectNotFoundException;

/**
 * @author Mark Johnson
 */
public abstract class AbstractAutomaticLookupDependency<T> extends AbstractDependency<T> {

    protected abstract void tryResolvingWithLookup(T lookup);

    @Override
    public void tryResolving(final DependencyResolver<? super T> dependencyResolver) throws DependencyResolutionException {
        try {
            final T resolutionObject = dependencyResolver.<T>getResolutionObject(getDependantType());
            tryResolvingWithLookup(resolutionObject);
        } catch (final ResolutionObjectNotFoundException e) {
            throw new DependencyResolutionException(String.format("Exception encountered while retrieving resolution object: %s", e.getMessage()));
        }
    }
}
