package pw.stamina.munition.test.tests;

import org.junit.Test;
import pw.stamina.munition.event.annotated.Reactive;
import pw.stamina.munition.event.core.Event;
import pw.stamina.munition.event.core.ScanningEventBus;
import pw.stamina.munition.event.core.registration.Registration;
import pw.stamina.munition.event.core.registration.registry.SimpleRegistry;
import pw.stamina.munition.event.core.routing.SimpleRouter;
import pw.stamina.munition.event.annotated.method.SimpleAnnotatedMethodEventBus;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Mark Johnson
 */
public class AnnotatedMethodEventBusTests {

    @Test
    public void testUsingMethodBus() {
        final ScanningEventBus<Class<?>, Event<Object>, Object> bus = new SimpleAnnotatedMethodEventBus<>(
                Object.class,
                new SimpleRegistry<>(),
                new SimpleRouter<>()
        );

        final String[] str = new String[1];
        final Object registrant = new Object() {
            @Reactive
            private void onEvent(final Event<String> event) {
                str[0] = event.getData();
            }
        };

        final List<? extends Registration<?, ?>> results = bus.scan(registrant).getCompletedRegistrations();

        bus.notify(String.class, Event.wrap("Foo"));

        assertEquals("Foo", str[0]);
    }
}
