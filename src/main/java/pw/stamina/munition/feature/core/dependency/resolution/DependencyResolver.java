package pw.stamina.munition.feature.core.dependency.resolution;

import java.lang.reflect.Type;

/**
 * @author Mark Johnson
 */
public interface DependencyResolver<T> {
    <U extends T> U getResolutionObject(Type type) throws ResolutionObjectNotFoundException;
}
