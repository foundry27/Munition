package pw.stamina.munition.event.annotated.field;

import pw.stamina.munition.event.annotated.ScanResultCollector;
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
public class SimpleAnnotatedFieldEventBus<K, E, R> extends AbstractAnnotatedFieldEventBus<K, Event<E>, R> {

    public SimpleAnnotatedFieldEventBus(final Registry<K, BiConsumer<K, Event<E>>> registrations, final Router<K, Event<E>> router) {
        super(registrations, router);
    }

    @Override
    public ScanResult<K, Event<E>> scan(final R registrant) throws ScanFailedException {
        return Arrays.stream(registrant.getClass().getDeclaredFields())
                .filter(SimpleAnnotatedFieldEventBus::checkIfValidFieldAndEnsureConsistency)
                .map(field -> getFieldContents(field, registrant))
                .map(this::tryCreatingRegistrationFromFieldContent)
                .collect(new ScanResultCollector<>());
    }

}
