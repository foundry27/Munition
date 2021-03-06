package pw.stamina.munition.event.core.routing;

import pw.stamina.munition.event.core.registration.Registration;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Mark Johnson
 */
public class ExceptionHandlingRouterDecorator<K, V> implements Router<K, V> {

    private final Router<K, V> backingRouter;

    private final Consumer<Throwable> throwableConsumer;

    public ExceptionHandlingRouterDecorator(final Router<K, V> backingRouter, final Consumer<Throwable> throwableConsumer) {
        this.backingRouter = Objects.requireNonNull(backingRouter, "The backing router cannot be null");
        this.throwableConsumer = Objects.requireNonNull(throwableConsumer, "The throwable consumer cannot be null");
    }

    @Override
    public void route(final K key, final V value, final Stream<Registration<? extends K, BiConsumer<K, V>>> consumers) {
        try {
            backingRouter.route(key, value, consumers);
        } catch (final Throwable t) {
            throwableConsumer.accept(t);
        }
    }
}
