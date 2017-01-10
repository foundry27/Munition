package pw.stamina.munition.event.core.registration.registry;

import pw.stamina.munition.event.core.registration.Registration;
import pw.stamina.munition.event.core.selection.Selector;

import java.util.stream.Stream;

/**
 * @author Mark Johnson
 */
public interface Registry<K, V> extends Iterable<Registration<? extends K, V>> {
    Registration<? extends K, V> register(Selector<? extends K> selector, V object);

    boolean unregister(K key);

    Stream<Registration<? extends K, V>> select(K key);

    void clear();

    long size();
}