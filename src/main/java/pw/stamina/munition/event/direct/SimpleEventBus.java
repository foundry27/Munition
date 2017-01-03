package pw.stamina.munition.event.direct;

import pw.stamina.munition.event.core.AbstractEventBus;
import pw.stamina.munition.event.core.Event;
import pw.stamina.munition.event.core.registration.registry.Registry;
import pw.stamina.munition.event.core.routing.Router;

import java.util.function.BiConsumer;

/**
 * @author Mark Johnson
 */
public class SimpleEventBus<K, E> extends AbstractEventBus<K, Event<E>> {
    public SimpleEventBus(final Registry<K, BiConsumer<K, Event<E>>> registrations, final Router<K, Event<E>> router) {
        super(registrations, router);
    }
}
