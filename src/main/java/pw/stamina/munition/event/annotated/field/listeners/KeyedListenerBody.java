package pw.stamina.munition.event.annotated.field.listeners;

import pw.stamina.munition.event.core.Event;

import java.util.function.BiConsumer;

/**
 * @author Mark Johnson
 */
@FunctionalInterface
public interface KeyedListenerBody<K, E> extends BiConsumer<K, Event<E>> {
    @Override
    void accept(K key, Event<E> event);
}
