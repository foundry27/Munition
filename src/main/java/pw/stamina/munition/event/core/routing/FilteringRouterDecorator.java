package pw.stamina.munition.event.core.routing;

import pw.stamina.munition.event.core.filtering.KeyedFilter;
import pw.stamina.munition.event.core.registration.Registration;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 * @author Mark Johnson
 */
public class FilteringRouterDecorator<K, V> implements Router<K, V> {

    private final Router<K, V> backingRouter;

    private final Iterable<KeyedFilter<Registration<? extends K, BiConsumer<K, V>>, V>> filters;

    public FilteringRouterDecorator(final Router<K, V> backingRouter, final Iterable<KeyedFilter<Registration<? extends K, BiConsumer<K, V>>, V>> filters) {
        this.backingRouter = backingRouter;
        this.filters = filters;
    }

    @Override
    public void route(final K key, final V value, Stream<Registration<? extends K, BiConsumer<K, V>>> consumers) {
        for (final KeyedFilter<Registration<? extends K, BiConsumer<K, V>>, V> filter : filters) {
            consumers = filter.filter(consumers, value);
        }
        backingRouter.route(key, value, consumers);
    }
}
