package pw.stamina.munition.test.tests;

import org.junit.Test;
import pw.stamina.munition.event.annotated.Reactive;
import pw.stamina.munition.event.annotated.field.SimpleAnnotatedFieldEventBus;
import pw.stamina.munition.event.annotated.field.listeners.Listener;
import pw.stamina.munition.event.core.Event;
import pw.stamina.munition.event.core.ScanningEventBus;
import pw.stamina.munition.core.traits.Cancellable;
import pw.stamina.munition.event.core.registration.Registration;
import pw.stamina.munition.event.core.registration.registry.SimpleRegistry;
import pw.stamina.munition.event.core.routing.SimpleRouter;

import java.util.List;
import java.util.function.BiConsumer;

import static org.junit.Assert.assertEquals;
import static pw.stamina.munition.event.core.selection.Selectors.$;

/**
 * @author Mark Johnson
 */
public class AnnotatedEventBusTests {

    @Test
    public void testAnnotatedListenerRegistrationAndCancellation() {
        final ScanningEventBus<Object, Event<Object>, Object> bus = new SimpleAnnotatedFieldEventBus<>(
                new SimpleRegistry<>(),
                new SimpleRouter<>()
        );

        final int[] receivedInteger = new int[1];

        final Object registrant = new Object() {
            @Reactive
            private final Listener<String, Integer> onIntegerEvent = new Listener<>(event -> {
                receivedInteger[0] = event.getData();
            }, $("64_sender"));
        };


        final List<Registration<?, BiConsumer<Object, Event<Object>>>> results = bus.scan(registrant).getCompletedRegistrations();

        assertEquals(1, results.size());

        bus.notify("64_sender", Event.wrap(64));

        assertEquals(64, receivedInteger[0], 0);


        results.forEach(Cancellable::cancel);

        assertEquals(false, bus.respondsToKey("64_sender"));
    }
}
