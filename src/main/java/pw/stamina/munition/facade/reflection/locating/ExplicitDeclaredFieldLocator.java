package pw.stamina.munition.facade.reflection.locating;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author Mark Johnson
 */
public final class ExplicitDeclaredFieldLocator implements FieldLocator {

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ExplicitDeclaredFieldLocator that = (ExplicitDeclaredFieldLocator) o;
        return Objects.equals(fieldName, that.fieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName);
    }

    @Override
    public String toString() {
        return "ExplicitDeclaredFieldLocator{" +
                "fieldName='" + fieldName + '\'' +
                '}';
    }
}
