package pw.stamina.munition.test.tests;

import org.junit.Test;
import pw.stamina.munition.event.core.Event;
import pw.stamina.munition.event.core.EventBus;
import pw.stamina.munition.event.core.registration.registry.SimpleRegistry;
import pw.stamina.munition.event.core.routing.SimpleRouter;
import pw.stamina.munition.event.core.selection.Selectors;
import pw.stamina.munition.event.direct.SimpleEventBus;
import pw.stamina.munition.input.keyboard.EventDrivenKeyListenerProvider;
import pw.stamina.munition.input.keyboard.KeyListener;
import pw.stamina.munition.input.keyboard.KeyListenerProvider;
import pw.stamina.munition.input.keyboard.KeyboardKey;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Foundry
 */
public class KeyListenerTests {
    @Test
    public void testUsingStickyEventDrivenKeyListener() {
        final EventBus<Object, Event<Object>> bus = new SimpleEventBus<>(new SimpleRegistry<>(), new SimpleRouter<>());

        final Set<KeyboardKey<Character>> keys = Collections.singleton(KeyboardKey.from('A', 'A'));

        final KeyListenerProvider<Character> listenerProvider = new EventDrivenKeyListenerProvider<>(bus, key -> {
            return Selectors.$(new KeyInteraction<>(KeyInteraction.Type.PRESS, key));
        }, key -> {
            return Selectors.$(new KeyInteraction<>(KeyInteraction.Type.RELEASE, key));
        });


        final AtomicInteger pressCount = new AtomicInteger();

        final KeyListener<Character> listener = listenerProvider.on(keys, pressCount::incrementAndGet);


        bus.notify(new KeyInteraction<>(KeyInteraction.Type.PRESS, KeyboardKey.from('A', 'A')), Event.empty());

        assertEquals(1, pressCount.get());

        bus.notify(new KeyInteraction<>(KeyInteraction.Type.PRESS, KeyboardKey.from('A', 'A')), Event.empty());

        assertEquals(1, pressCount.get());

        bus.notify(new KeyInteraction<>(KeyInteraction.Type.RELEASE, KeyboardKey.from('A', 'A')), Event.empty());

        bus.notify(new KeyInteraction<>(KeyInteraction.Type.PRESS, KeyboardKey.from('A', 'A')), Event.empty());

        assertEquals(2, pressCount.get());


        listener.pause();

        bus.notify(new KeyInteraction<>(KeyInteraction.Type.RELEASE, KeyboardKey.from('A', 'A')), Event.empty());

        bus.notify(new KeyInteraction<>(KeyInteraction.Type.PRESS, KeyboardKey.from('A', 'A')), Event.empty());

        assertEquals(2, pressCount.get());


        listener.resume();

        bus.notify(new KeyInteraction<>(KeyInteraction.Type.RELEASE, KeyboardKey.from('A', 'A')), Event.empty());

        bus.notify(new KeyInteraction<>(KeyInteraction.Type.PRESS, KeyboardKey.from('A', 'A')), Event.empty());

        assertEquals(3, pressCount.get());


        assertTrue(bus.respondsToKey(new KeyInteraction<>(KeyInteraction.Type.PRESS, KeyboardKey.from('A', 'A'))));

        listener.cancel();

        assertFalse(bus.respondsToKey(new KeyInteraction<>(KeyInteraction.Type.PRESS, KeyboardKey.from('A', 'A'))));
    }

    private static final class KeyInteraction<K> {
        final Type type;

        final KeyboardKey<K> key;

        KeyInteraction(final Type type, final KeyboardKey<K> key) {
            this.type = type;
            this.key = key;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof KeyInteraction)) return false;
            final KeyInteraction<?> that = (KeyInteraction<?>) o;
            return type == that.type &&
                    Objects.equals(key, that.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, key);
        }

        enum Type {
            PRESS, RELEASE
        }
    }
}
