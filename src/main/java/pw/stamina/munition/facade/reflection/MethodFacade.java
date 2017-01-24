package pw.stamina.munition.facade.reflection;

import pw.stamina.munition.facade.OptionalFacade;
import pw.stamina.munition.facade.reflection.locating.MethodLocator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Objects;

/**
 * @author Mark Johnson
 */
public final class MethodFacade extends AbstractMemberFacade<Method, MethodLocator> implements OptionalFacade<Method> {

    private MethodFacade(final ClassFacade classView, final MethodLocator methodLocator) {
        super(classView, methodLocator);
    }

    public static MethodFacade of(final ClassFacade classView, final MethodLocator methodLocator) {
        return new MethodFacade(classView, methodLocator);
    }

    public Object invoke(final Object instance, final Object... arguments) throws InvocationTargetException {
        if (isPresent()) {
            AccessController.doPrivileged((PrivilegedAction<Void>)() -> {
                backingMember.setAccessible(true);
                return null;
            });
            try {
                return backingMember.invoke(instance, arguments);
            } catch (final IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MethodFacade that = (MethodFacade) o;
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
        return "MethodFacade{" +
                "classFacade=" + classFacade +
                ", memberLocator=" + memberLocator +
                ", presenceChecked=" + presenceChecked +
                ", backingMember=" + backingMember +
                '}';
    }

}
