package pw.stamina.munition.input.keyboard;

import java.util.Collection;
import java.util.Set;

/**
 * @author Mark Johnson
 */
public final class NonnullValidatingKeyListenerProvider<K> implements KeyListenerProvider<K> {

    private final KeyListenerProvider<K> backingProvider;

    public NonnullValidatingKeyListenerProvider(final KeyListenerProvider<K> backingProvider) {
        this.backingProvider = backingProvider;
    }

    @Override
    public KeyListener<K> on(final Set<KeyboardKey<K>> keyboardKeys, final Runnable keyPressRunnable) {
        validateArguments(keyboardKeys, keyPressRunnable);
        return backingProvider.on(keyboardKeys, keyPressRunnable);
    }

    private static void validateArguments(final Collection<?> keyboardKeys, final Runnable keyPressRunnable) {
        if (keyboardKeys == null) {
            throw new NullPointerException("The keyboard key set cannot be null");
        } else if (keyboardKeys.contains(null)) {
            throw new NullPointerException("The keyboard key set cannot contain null");
        }
        if (keyPressRunnable == null) {
            throw new NullPointerException("The key press runnable cannot be null");
        }
    }
}
