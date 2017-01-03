package pw.stamina.munition.input.keyboard;

import java.util.Set;

/**
 * @author Foundry
 */
public abstract class AbstractKeyListener<K> implements KeyListener<K> {

    protected boolean paused;

    protected boolean cancelled;

    protected AbstractKeyListener() {}

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void cancel() {
        cancelled = true;
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public abstract Set<KeyboardKey<K>> getListenedKeys();
}
