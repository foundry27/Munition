package pw.stamina.munition.event.annotated;

import pw.stamina.munition.event.core.AbstractEventBus;
import pw.stamina.munition.event.core.ScanningEventBus;
import pw.stamina.munition.event.core.registration.registry.Registry;
import pw.stamina.munition.event.core.routing.Router;

import java.util.function.BiConsumer;

/**
 * @author Mark Johnson
 */
public abstract class AbstractAnnotatedEventBus<K, E, R> extends AbstractEventBus<K, E> implements ScanningEventBus<K, E, R> {
    protected AbstractAnnotatedEventBus(final Registry<K, BiConsumer<K, E>> registrations, final Router<K, E> router) {
        super(registrations, router);
    }

    @Override
    public ScanningEventBus<K, E, R> notify(final K key, final E event) {
        super.notify(key, event);
        return this;
    }
}
