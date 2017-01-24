package pw.stamina.munition.facade;

import java.util.Optional;

/**
 * @author Mark Johnson
 */
public interface OptionalFacade<T> extends Facade<T> {
    @Override
    T reify() throws ReificationException;

    boolean isPresent();

    default Optional<T> asOptional() {
        return isPresent() ? Optional.of(reify()) : Optional.empty();
    }
}
