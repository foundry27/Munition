package pw.stamina.munition.feature.plugin.dependency.resolution;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

/**
 * @author Mark Johnson
 */
public class SimpleDependencyResolver<T> implements DependencyResolver<T> {

    private final Map<Type, T> dependencyMap;

    public SimpleDependencyResolver(final Map<Type, T> dependencyMap) {
        this.dependencyMap = Objects.requireNonNull(dependencyMap, "The dependency map cannot be null");
    }

    @Override
    public <U extends T> U getResolutionObject(final Type type) throws ResolutionObjectNotFoundException {
        @SuppressWarnings("unchecked")
        final U lookup = (U) dependencyMap.get(type);
        if (lookup == null) {
            throw new ResolutionObjectNotFoundException(String.format("There is no resolution object available matching the type signature %s", type));
        } else {
            return lookup;
        }
    }
}