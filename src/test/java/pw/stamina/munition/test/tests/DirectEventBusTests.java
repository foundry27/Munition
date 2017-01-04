package pw.stamina.munition.test.tests;

import org.junit.Test;
import pw.stamina.munition.event.core.EventBus;
import pw.stamina.munition.event.core.Event;
import pw.stamina.munition.event.core.registration.Registration;
import pw.stamina.munition.event.core.registration.registry.SimpleRegistry;
import pw.stamina.munition.event.core.routing.SimpleRouter;
import pw.stamina.munition.event.core.selection.ClassSelector;
import pw.stamina.munition.event.core.SimpleEventBus;

import static org.junit.Assert.assertEquals;
import static pw.stamina.munition.event.core.selection.Selectors.$;

/**
 * @author Mark Johnson
 */
public class DirectEventBusTests {
    @Test
    public void testUsingObjectSelector() {
        final EventBus<Object, Event<Object>> bus = new SimpleEventBus<>(
                new SimpleRegistry<>(),
                new SimpleRouter<>()
        );

        {
            final boolean[] stringRegCalled = new boolean[1];

            final Registration<?, ?> stringReg = bus.on($("testStringKey"), (event) -> stringRegCalled[0] = true);

            bus.notify("testStringKey", Event.empty());

            assertEquals(true, stringRegCalled[0]);

            stringReg.cancel();

            assertEquals(false, bus.respondsToKey("testStringKey"));
        }

        {
            final boolean[] intRegCalled = new boolean[1];

            final Registration<?, ?> intReg = bus.on($(512), (event) -> intRegCalled[0] = true);

            bus.notify(512, Event.empty());

            assertEquals(true, intRegCalled[0]);

            intReg.cancel();

            assertEquals(false, bus.respondsToKey(512));
        }

        {
            final boolean[] otherRegCalled = new boolean[1];

            final Registration<?, ?> otherReg = bus.on($(bus), (event) -> otherRegCalled[0] = true);

            bus.notify(bus, Event.empty());

            assertEquals(true, otherRegCalled[0]);

            otherReg.cancel();

            assertEquals(false, bus.respondsToKey(bus));
        }
    }

    @Test
    public void testUsingClassSelector() {
        final EventBus<Object, Event<Object>> bus = new SimpleEventBus<>(
                new SimpleRegistry<>(),
                new SimpleRouter<>()
        );

        final boolean[] regCalled = new boolean[1];

        final Registration<?, ?> reg = bus.on(new ClassSelector<>(Number.class), (event) -> regCalled[0] = true);

        bus.notify(512.0, Event.empty());

        assertEquals(true, regCalled[0]);

        reg.cancel();

        assertEquals(false, bus.respondsToKey(512.0));
    }
}
