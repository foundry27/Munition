package pw.stamina.munition.facade.reflection;

import pw.stamina.munition.facade.OptionalFacade;
import pw.stamina.munition.facade.reflection.locating.FieldLocator;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author Mark Johnson
 */
public final class FieldFacade extends AbstractMemberFacade<Field, FieldLocator> implements OptionalFacade<Field> {

    private FieldFacade(final ClassFacade classView, final FieldLocator fieldLocator) {
        super(classView, fieldLocator);
    }

    public static FieldFacade of(final ClassFacade classView, final FieldLocator fieldLocator) {
        return new FieldFacade(classView, fieldLocator);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FieldFacade that = (FieldFacade) o;
        return Objects.equals(classFacade, that.classFacade) &&
                Objects.equals(memberLocator, that.memberLocator) &&
                Objects.equals(backingMember, that.backingMember);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classFacade, memberLocator, backingMember);
    }

    @Override
    public String toString() {
        return "FieldFacade{" +
                "classFacade=" + classFacade +
                ", memberLocator=" + memberLocator +
                ", presenceChecked=" + presenceChecked +
                ", backingMember=" + backingMember +
                '}';
    }

}
