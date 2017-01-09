package pw.stamina.munition.input.keyboard;

import pw.stamina.munition.event.core.EventBus;
import pw.stamina.munition.event.core.selection.Selector;

import java.util.function.Function;

/**
 * @author Mark Johnson
 */
public final class KeyListeners {

    private KeyListeners() {}

    public static <K, E> KeyListenerProvider<K> makeEventDrivenProvider(final EventBus<? super E, ?> eventBus,
                                                                     final Function<KeyboardKey<K>, Selector<? extends E>> keyPressSelectorCreator,
                                                                     final Function<KeyboardKey<K>, Selector<? extends E>> keyReleaseSelectorCreator) {
        return new NonnullValidatingKeyListenerProvider<>(
                new EventDrivenKeyListenerProvider<>(eventBus, keyPressSelectorCreator, keyReleaseSelectorCreator));
    }
}
