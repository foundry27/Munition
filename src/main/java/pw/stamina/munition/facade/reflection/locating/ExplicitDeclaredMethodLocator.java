package pw.stamina.munition.facade.reflection.locating;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Mark Johnson
 */
public final class ExplicitDeclaredMethodLocator implements MethodLocator {

    private final String methodName;

    private final Class<?>[] parameterTypes;

    public ExplicitDeclaredMethodLocator(final String methodName, final Class<?>... parameterTypes) {
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
    }

    @Override
    public Method locate(final Class<?> targetClass) throws LocationException {
        try {
            return targetClass.getDeclaredMethod(methodName, parameterTypes);
        } catch (final NoSuchMethodException e) {
            throw new LocationException(String.format("Class %s does not have a declared method named '%s' with the parameter types %s",
                    targetClass.getCanonicalName(), methodName, Arrays.toString(parameterTypes)));
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ExplicitDeclaredMethodLocator that = (ExplicitDeclaredMethodLocator) o;
        return Objects.equals(methodName, that.methodName) &&
                Arrays.equals(parameterTypes, that.parameterTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodName, parameterTypes);
    }

    @Override
    public String toString() {
        return "ExplicitDeclaredMethodLocator{" +
                "methodName='" + methodName + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                '}';
    }
}
