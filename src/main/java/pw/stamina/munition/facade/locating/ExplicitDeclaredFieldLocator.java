package pw.stamina.munition.facade.locating;

import java.lang.reflect.Field;

/**
 * @author Mark Johnson
 */
public class ExplicitDeclaredFieldLocator implements FieldLocator {

    private final String fieldName;

    public ExplicitDeclaredFieldLocator(final String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public Field locate(final Class<?> targetClass) throws LocationException {
        try {
            return targetClass.getDeclaredField(fieldName);
        } catch (final NoSuchFieldException e) {
            throw new LocationException(String.format("Class %s does not have a declared field named '%s'",
                    targetClass.getCanonicalName(), fieldName));
        }
    }
}
