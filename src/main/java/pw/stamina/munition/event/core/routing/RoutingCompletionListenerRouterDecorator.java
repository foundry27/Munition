package pw.stamina.munition.event.core.routing;

import pw.stamina.munition.event.core.registration.Registration;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Mark Johnson
 */
public class RoutingCompletionListenerRouterDecorator<K, V> implements Router<K, V> {

    private final Router<K, V> backingRouter;

    private final Consumer<V> valueConsumer;

    public RoutingCompletionListenerRouterDecorator(final Router<K, V> backingRouter, final Consumer<V> valueConsumer) {
        this.backingRouter = Objects.requireNonNull(backingRouter, "The backing router cannot be null");
        this.valueConsumer = Objects.requireNonNull(valueConsumer, "The value consumer cannot be null");
    }

    @Override
    public void route(final K key, final V value, final Stream<Registration<? extends K, BiConsumer<K, V>>> consumers) {
        try {
            backingRouter.route(key, value, consumers);
        } finally {
            valueConsumer.accept(value);
        }
    }
}
