package pw.stamina.munition.input.keyboard;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Mark Johnson
 */
public class StickyCallbackKeyListener<K> extends AbstractCallbackKeyListener<K> {

    private boolean hasCallCycleCompleted;

    public StickyCallbackKeyListener(final Collection<KeyboardKey<K>> listenedKeys,
                                     final Runnable keysPressedCallback,
                                     final Consumer<Consumer<KeyboardKey<K>>> keyPressCallbackConsumer,
                                     final Consumer<Consumer<KeyboardKey<K>>> keyReleaseCallbackConsumer,
                                     final Runnable pauseAction,
                                     final Runnable resumeAction,
                                     final Runnable cancelAction) {
        super(listenedKeys, keysPressedCallback,
                keyPressCallbackConsumer, keyReleaseCallbackConsumer,
                pauseAction, resumeAction, cancelAction);
    }

    @Override
    protected void handleRegisteringKeyCallbacks(final Consumer<Consumer<KeyboardKey<K>>> keyPressCallbackConsumer,
                                                 final Consumer<Consumer<KeyboardKey<K>>> keyReleaseCallbackConsumer) {
        keyPressCallbackConsumer.accept(keyboardKey -> {
            if (!cancelled && !paused) {
                listenedKeys.replace(keyboardKey, Boolean.FALSE, Boolean.TRUE);
                if (areAllKeysPressed() && !hasCallCycleCompleted) {
                    keysPressedCallback.run();
                    hasCallCycleCompleted = true;
                }
            }
        });

        keyReleaseCallbackConsumer.accept(keyboardKey -> {
            if (!cancelled && !paused) {
                listenedKeys.replace(keyboardKey, Boolean.TRUE, Boolean.FALSE);
                hasCallCycleCompleted = false;
            }
        });
    }
}
