package pw.stamina.munition.input.keyboard;

import pw.stamina.munition.core.traits.Cancellable;
import pw.stamina.munition.core.traits.Pausable;
import pw.stamina.munition.event.core.EventBus;
import pw.stamina.munition.event.core.registration.Registration;
import pw.stamina.munition.event.core.selection.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Foundry
 */
public class EventDrivenKeyListenerProvider<K, E> implements KeyListenerProvider<K> {

    private final KeyListenerProvider<K> backingKeyListenerProvider;

    private final List<Registration<?, ?>> registrationList;

    public EventDrivenKeyListenerProvider(final EventBus<? super E, ?> eventBus,
                                          final Function<KeyboardKey<K>, Selector<? extends E>> keyPressSelectorCreator,
                                          final Function<KeyboardKey<K>, Selector<? extends E>> keyReleaseSelectorCreator) {
        this.registrationList = new ArrayList<>();
        this.backingKeyListenerProvider = new DeferringKeyListenerProvider<>((keyConsumer, key) -> {
            registrationList.add(eventBus.on(keyPressSelectorCreator.apply(key), e -> {
                keyConsumer.accept(key);
            }));
        }, (keyConsumer, key) -> {
            registrationList.add(eventBus.on(keyReleaseSelectorCreator.apply(key), e -> {
                keyConsumer.accept(key);
            }));
        }, () -> registrationList.forEach(Pausable::pause), () -> registrationList.forEach(Pausable::resume), () -> registrationList.forEach(Cancellable::cancel));
    }

    @Override
    public KeyListener<K> on(final Set<KeyboardKey<K>> keyboardKeys, final Runnable keyPressRunnable) {
        return backingKeyListenerProvider.on(keyboardKeys, keyPressRunnable);
    }

}
