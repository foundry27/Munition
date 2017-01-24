package pw.stamina.munition.facade.reflection.locating;

import java.lang.reflect.Field;

/**
 * @author Mark Johnson
 */
public interface FieldLocator extends MemberLocator<Field> {
    Field locate(Class<?> targetClass);
}
