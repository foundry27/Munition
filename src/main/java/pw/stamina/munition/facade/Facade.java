package pw.stamina.munition.facade;

/**
 * @author Mark Johnson
 */
public interface Facade<T> {
    T reify() throws ReificationException;
}
