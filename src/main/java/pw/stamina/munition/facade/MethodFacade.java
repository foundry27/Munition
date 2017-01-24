package pw.stamina.munition.facade;

import pw.stamina.munition.facade.locating.MethodLocator;

import java.lang.reflect.Method;

/**
 * @author Mark Johnson
 */
public class MethodFacade implements OptionalFacade<Method> {

    private final ClassFacade classView;

    private final MethodLocator methodLocator;

    private boolean presenceChecked;

    private Method backingMethod;

    private MethodFacade(final ClassFacade classView, final MethodLocator methodLocator) {
        this.classView = classView;
        this.methodLocator = methodLocator;
    }

    public static MethodFacade of(final ClassFacade classView, final MethodLocator methodLocator) {
        return new MethodFacade(classView, methodLocator);
    }

    @Override
    public Method reify() throws ReificationException {
        if (presenceChecked) {
            return backingMethod;
        } else {
            presenceChecked = true;
            if (classView.isPresent()) {
                backingMethod = methodLocator.locate(classView.reify());
            } else {
                throw new ReificationException(String.format("Class %s does not exist", classView.getName()));
            }
            return backingMethod;
        }
    }

    @Override
    public boolean isPresent() {
        if (presenceChecked) {
            return backingMethod != null;
        } else {
            try {
                reify();
                return true;
            } catch (final ReificationException e) {
                return false;
            }
        }
    }
}
