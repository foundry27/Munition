package pw.stamina.munition.input.keyboard;

import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Mark Johnson
 */
public class DeferringKeyListenerProvider<K> implements KeyListenerProvider<K> {

    private final BiConsumer<Consumer<KeyboardKey<K>>, KeyboardKey<K>> listenerSubscriber;

    private final BiConsumer<Consumer<KeyboardKey<K>>, KeyboardKey<K>> listenerUnsubscriber;

    private final Runnable pauseAction;

    private final Runnable resumeAction;

    private final Runnable cancelAction;

    public DeferringKeyListenerProvider(final BiConsumer<Consumer<KeyboardKey<K>>, KeyboardKey<K>> listenerSubscriber,
                                        final BiConsumer<Consumer<KeyboardKey<K>>, KeyboardKey<K>> listenerUnsubscriber,
                                        final Runnable pauseAction,
                                        final Runnable resumeAction,
                                        final Runnable cancelAction) {
        this.listenerSubscriber = Objects.requireNonNull(listenerSubscriber, "The listener subscription consumer cannot be null");
        this.listenerUnsubscriber = Objects.requireNonNull(listenerUnsubscriber, "The listener unsubscription consumer cannot be null");
        this.pauseAction = Objects.requireNonNull(pauseAction, "The pause action cannot be null");
        this.resumeAction = Objects.requireNonNull(resumeAction, "The resume action cannot be null");
        this.cancelAction = Objects.requireNonNull(cancelAction, "The cancellation action cannot be null");
    }

    @Override
    public KeyListener<K> on(final Set<KeyboardKey<K>> keyboardKeys, final Runnable keyPressRunnable) {
        return new StickyCallbackKeyListener<>(keyboardKeys, keyPressRunnable,
                keyboardKeyPressConsumer -> keyboardKeys.forEach(key -> listenerSubscriber.accept(keyboardKeyPressConsumer, key)),
                keyboardKeyReleaseConsumer -> keyboardKeys.forEach(key -> listenerUnsubscriber.accept(keyboardKeyReleaseConsumer, key)),
                pauseAction, resumeAction, cancelAction);
    }
}
