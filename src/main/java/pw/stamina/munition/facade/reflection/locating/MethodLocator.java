package pw.stamina.munition.facade.reflection.locating;

import java.lang.reflect.Method;

/**
 * @author Mark Johnson
 */
public interface MethodLocator extends MemberLocator<Method> {
    Method locate(Class<?> targetClass);
}
