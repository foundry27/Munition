package pw.stamina.munition.facade.reflection;

import pw.stamina.munition.facade.OptionalFacade;
import pw.stamina.munition.facade.ReificationException;
import pw.stamina.munition.facade.reflection.locating.ExplicitDeclaredFieldLocator;
import pw.stamina.munition.facade.reflection.locating.ExplicitDeclaredMethodLocator;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Mark Johnson
 */
public final class ClassFacade implements OptionalFacade<Class<?>> {

    private final String backingClassName;

    private final ClassLoader classLoader;

    private boolean presenceChecked;

    private Class<?> backingClass;

    private ClassFacade(final String backingClassName, final ClassLoader classLoader) {
        this.backingClassName = backingClassName;
        this.classLoader = classLoader;
    }

    public static ClassFacade of(final String backingClassName) {
        return new ClassFacade(backingClassName, ClassFacade.class.getClassLoader());
    }

    @Override
    public Class<?> reify() throws ReificationException {
        if (presenceChecked) {
            return backingClass;
        } else {
            presenceChecked = true;
            try {
                backingClass = Class.forName(backingClassName, true, classLoader);
            } catch (final ClassNotFoundException var2) {
                throw new ReificationException("Class not present: " + backingClassName);
            }
            return backingClass;
        }
    }

    @Override
    public boolean isPresent() {
        if (presenceChecked) {
            return backingClass != null;
        } else {
            try {
                reify();
                return true;
            } catch (final ReificationException e) {
                return false;
            }
        }
    }

    public String getName() {
        return backingClassName;
    }

    @Override
    public Optional<Class<?>> asOptional() {
        if (isPresent()) {
            return Optional.of(backingClass);
        } else {
            return Optional.empty();
        }
    }

    public FieldFacade getField(final String fieldName) {
        return FieldFacade.of(this, new ExplicitDeclaredFieldLocator(fieldName));
    }

    public MethodFacade getMethod(final String methodName, final Class<?>... parameterTypes) {
        return MethodFacade.of(this, new ExplicitDeclaredMethodLocator(methodName, parameterTypes));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ClassFacade that = (ClassFacade) o;
        return Objects.equals(backingClassName, that.backingClassName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(backingClassName);
    }

    @Override
    public String toString() {
        return "ClassFacade{" +
                "backingClassName='" + backingClassName + '\'' +
                ", classLoader=" + classLoader +
                ", presenceChecked=" + presenceChecked +
                ", backingClass=" + backingClass +
                '}';
    }
}
