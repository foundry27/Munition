package pw.stamina.munition.input.keyboard;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Mark Johnson
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

        this.listenedKeys = Objects.requireNonNull(listenedKeys, "The listened keys collection cannot be null")
                .stream()
                .collect(Collectors.toMap(Function.identity(), v -> Boolean.FALSE));
        this.keysPressedCallback = Objects.requireNonNull(keysPressedCallback, "The key press callback cannot be null");
        this.pauseAction = Objects.requireNonNull(pauseAction, "The pause action cannot be null");
        this.resumeAction = Objects.requireNonNull(resumeAction, "The resume action cannot be null");
        this.cancelAction = Objects.requireNonNull(cancelAction, "The cancellation action cannot be null");

        handleRegisteringKeyCallbacks(Objects.requireNonNull(keyPressCallbackConsumer, "The key press callback consumer cannot be null"),
                Objects.requireNonNull(keyReleaseCallbackConsumer, "The key release callback consumer cannot be null"));
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
