package pw.stamina.munition.input.keyboard;

import pw.stamina.munition.core.traits.Cancellable;
import pw.stamina.munition.core.traits.Pausable;

import java.util.Set;

/**
 * @author Mark Johnson
 */
public interface KeyListener<K> extends Cancellable, Pausable {
    @Override
    void cancel();

    boolean isCancelled();

    @Override
    void resume();

    @Override
    void pause();

    boolean isPaused();

    Set<KeyboardKey<K>> getListenedKeys();
}
