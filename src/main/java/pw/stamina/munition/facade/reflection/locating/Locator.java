package pw.stamina.munition.facade.reflection.locating;

/**
 * @author Mark Johnson
 */
public interface Locator<T, C> {
    T locate(C container) throws LocationException;
}
