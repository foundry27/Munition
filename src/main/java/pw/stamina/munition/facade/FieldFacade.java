package pw.stamina.munition.facade;

import pw.stamina.munition.facade.locating.FieldLocator;

import java.lang.reflect.Field;

/**
 * @author Mark Johnson
 */
public class FieldFacade implements OptionalFacade<Field> {

    private final ClassFacade classView;

    private final FieldLocator fieldLocator;

    private boolean presenceChecked;

    private Field backingField;

    private FieldFacade(final ClassFacade classView, final FieldLocator fieldLocator) {
        this.classView = classView;
        this.fieldLocator = fieldLocator;
    }

    public static FieldFacade of(final ClassFacade classView, final FieldLocator fieldLocator) {
        return new FieldFacade(classView, fieldLocator);
    }

    @Override
    public Field reify() throws ReificationException {
        if (presenceChecked) {
            return backingField;
        } else {
            presenceChecked = true;
            if (classView.isPresent()) {
                backingField = fieldLocator.locate(classView.reify());
            } else {
                throw new ReificationException(String.format("Class %s does not exist", classView.getName()));
            }
            return backingField;
        }
    }

    @Override
    public boolean isPresent() {
        if (presenceChecked) {
            return backingField != null;
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
