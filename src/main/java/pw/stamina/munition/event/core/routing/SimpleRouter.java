package pw.stamina.munition.event.core.routing;

import pw.stamina.munition.event.core.registration.Registration;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 * @author Mark Johnson
 */
public class SimpleRouter<K, V> implements Router<K, V> {
    @Override
    public void route(final K key, final V value, final Stream<Registration<? extends K, BiConsumer<K, V>>> consumers) {
        consumers.filter(registration -> !registration.isPaused() && !registration.isCancelled())
                .forEach(registration -> registration.getObject().accept(key, value));
    }
}
