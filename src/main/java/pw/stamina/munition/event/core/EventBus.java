package pw.stamina.munition.event.core;

import pw.stamina.munition.event.core.registration.Registration;
import pw.stamina.munition.event.core.selection.Selector;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Mark Johnson
 */
public interface EventBus<K, E> {
    boolean respondsToKey(K key);

    Registration<? extends K, BiConsumer<K, E>> on(Selector<? extends K> selector, BiConsumer<K, E> keyAndEventConsumer);

    Registration<? extends K, BiConsumer<K, E>> on(Selector<? extends K> selector, Consumer<E> eventConsumer);

    EventBus<K, E> notify(K key, E event);
}
