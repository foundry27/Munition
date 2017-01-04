package pw.stamina.munition.event.core;

import pw.stamina.munition.event.core.registration.registry.Registry;
import pw.stamina.munition.event.core.routing.Router;

import java.util.function.BiConsumer;

/**
 * @author Mark Johnson
 */
public abstract class AbstractScanningEventBus<K, E, R> extends AbstractEventBus<K, E> implements ScanningEventBus<K, E, R> {
    protected AbstractScanningEventBus(final Registry<K, BiConsumer<K, E>> registrations, final Router<K, E> router) {
        super(registrations, router);
    }

    @Override
    public ScanningEventBus<K, E, R> notify(final K key, final E event) {
        super.notify(key, event);
        return this;
    }
}
