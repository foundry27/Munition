package pw.stamina.munition.input.keyboard;

import java.util.Set;

/**
 * @author Foundry
 */
public interface KeyListenerProvider<K> {
    KeyListener<K> on(Set<KeyboardKey<K>> keys, Runnable keyPressRunnable);
}