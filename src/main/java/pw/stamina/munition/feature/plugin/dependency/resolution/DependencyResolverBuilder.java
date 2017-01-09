package pw.stamina.munition.feature.plugin.dependency.resolution;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mark Johnson
 */
public class DependencyResolverBuilder<T> {
    private final Map<Type, T> dependencyMap = new HashMap<>();

    public DependencyResolverBuilder<T> withMapping(final Type type, final T value) {
        validateTypeCompatibility(type, value);
        dependencyMap.put(type, value);
        return this;
    }

    private static void validateTypeCompatibility(final Type type, final Object object) {
        if (type == null) {
            throw new NullPointerException("Null types are not supported by this builder");
        } else if (type instanceof Class) {
            if (!((Class) type).isInstance(object)) {
                throw new IllegalArgumentException(String.format("Type %s is not compatible with an object of type %s",
                        type.getTypeName(), object.getClass().getTypeName()));
            }
        } else if (type instanceof ParameterizedType) {
            if (!((Class) ((ParameterizedType) type).getRawType()).isInstance(object)) {
                throw new IllegalArgumentException(String.format("Type %s is not compatible with an object of type %s",
                        type.getTypeName(), object.getClass().getTypeName()));
            }
        }
    }

    public DependencyResolver<T> build() {
        return new SimpleDependencyResolver<>(dependencyMap);
    }
}
