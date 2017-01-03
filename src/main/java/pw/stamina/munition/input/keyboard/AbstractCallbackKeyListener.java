package pw.stamina.munition.input.keyboard;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Foundry
 */
public abstract class AbstractCallbackKeyListener<K> extends AbstractKeyListener<K> {

    protected final Map<KeyboardKey<K>, Boolean> listenedKeys;

    protected final Runnable keysPressedCallback;

    private final Runnable pauseAction;

    private final Runnable resumeAction;

    private final Runnable cancelAction;

    protected AbstractCallbackKeyListener(final Collection<KeyboardKey<K>> listenedKeys,
                                          final Runnable keysPressedCallback,
                                          final Consumer<Consumer<KeyboardKey<K>>> keyPressCallbackConsumer,
                                          final Consumer<Consumer<KeyboardKey<K>>> keyReleaseCallbackConsumer,
                                          final Runnable pauseAction,
                                          final Runnable resumeAction,
                                          final Runnable cancelAction) {

        this.listenedKeys = listenedKeys.stream().collect(Collectors.toMap(Function.identity(), v -> Boolean.FALSE));
        this.keysPressedCallback = keysPressedCallback;
        this.pauseAction = pauseAction;
        this.resumeAction = resumeAction;
        this.cancelAction = cancelAction;

        handleRegisteringKeyCallbacks(keyPressCallbackConsumer, keyReleaseCallbackConsumer);
    }

    protected abstract void handleRegisteringKeyCallbacks(final Consumer<Consumer<KeyboardKey<K>>> keyPressCallbackConsumer,
                                               final Consumer<Consumer<KeyboardKey<K>>> keyReleaseCallbackConsumer);

    protected boolean areAllKeysPressed() {
        return !listenedKeys.values().contains(Boolean.FALSE);
    }

    @Override
    public Set<KeyboardKey<K>> getListenedKeys() {
        return listenedKeys.keySet();
    }

    @Override
    public void cancel() {
        super.cancel();
        cancelAction.run();
        listenedKeys.clear();
    }

    @Override
    public void pause() {
        super.pause();
        pauseAction.run();
    }

    @Override
    public void resume() {
        super.resume();
        resumeAction.run();
    }
}
