package pw.stamina.munition.event.core.routing;

import pw.stamina.munition.event.core.registration.Registration;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 * @author Mark Johnson
 */
public interface Router<K, V> {
    void route(K key,
               V value,
               Stream<Registration<? extends K, BiConsumer<K, V>>> consumers);
}
