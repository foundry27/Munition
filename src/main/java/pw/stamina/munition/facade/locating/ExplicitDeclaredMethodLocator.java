package pw.stamina.munition.facade.locating;

import java.lang.reflect.Method;

/**
 * @author Mark Johnson
 */
public class ExplicitDeclaredMethodLocator implements MethodLocator {

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
            throw new LocationException(String.format("Class %s does not have a declared method named '%s'",
                    targetClass.getCanonicalName(), methodName));
        }
    }
}
