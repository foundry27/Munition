package pw.stamina.munition.event.core.registration;

/**
 * @author Mark Johnson
 */
public abstract class AbstractRegistration<K, T> implements Registration<K, T> {

    protected boolean paused;

    protected boolean cancelled;

    protected AbstractRegistration() {}

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
}
