package pw.stamina.munition.event.annotated.field.listeners;

import pw.stamina.munition.event.core.Event;

import java.util.function.Consumer;

/**
 * @author Mark Johnson
 */
@FunctionalInterface
public interface ListenerBody<E> extends Consumer<Event<E>> {
    @Override
    void accept(Event<E> event);
}
