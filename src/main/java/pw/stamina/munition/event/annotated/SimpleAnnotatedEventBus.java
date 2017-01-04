package pw.stamina.munition.event.annotated;

import pw.stamina.munition.event.core.Event;
import pw.stamina.munition.event.core.ScanFailedException;
import pw.stamina.munition.event.core.registration.registry.Registry;
import pw.stamina.munition.event.core.routing.Router;
import pw.stamina.munition.event.core.scanning.ScanResult;

import java.util.Arrays;
import java.util.function.BiConsumer;

/**
 * @author Mark Johnson
 */
public class SimpleAnnotatedEventBus<K, E, R> extends AbstractAnnotatedEventBus<K, Event<E>, R> {

    public SimpleAnnotatedEventBus(final Registry<K, BiConsumer<K, Event<E>>> registrations, final Router<K, Event<E>> router) {
        super(registrations, router);
    }

    @Override
    public ScanResult<K, Event<E>> scan(final R registrant) throws ScanFailedException {
        return Arrays.stream(registrant.getClass().getDeclaredFields())
                .filter(SimpleAnnotatedEventBus::checkIfValidFieldAndEnsureConsistency)
                .map(this::tryCreatingRegistrationFromFieldContent)
                .collect(new ScanResultCollector<>());
    }

}
