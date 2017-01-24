package pw.stamina.munition.facade;

import java.util.Optional;

/**
 * @author Mark Johnson
 */
public interface OptionalFacade<T> extends Facade<T> {
    @Override
    T reify() throws ReificationException;

    default boolean isPresent() {
        try {
            return reify() != null;
        } catch (final ReificationException e) {
            return false;
        }
    }

    default Optional<T> asOptional() {
        return isPresent() ? Optional.of(reify()) : Optional.empty();
    }
}
