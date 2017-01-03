package pw.stamina.munition.feature.core.dependency;

import pw.stamina.munition.feature.core.dependency.resolution.DependencyResolver;

import java.lang.reflect.Type;

/**
 * @author Mark Johnson
 */
public interface Dependency<T> {
    void tryResolving(DependencyResolver<? super T> dependencyResolver) throws DependencyResolutionException;

    ResolutionStatus getResolutionStatus();

    Type getDependantType();
}
