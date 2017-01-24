package pw.stamina.munition.facade;

import pw.stamina.munition.facade.locating.ExplicitDeclaredFieldLocator;
import pw.stamina.munition.facade.locating.ExplicitDeclaredMethodLocator;

import java.util.Optional;

/**
 * @author Mark Johnson
 */
public class ClassFacade implements OptionalFacade<Class<?>> {

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
}
