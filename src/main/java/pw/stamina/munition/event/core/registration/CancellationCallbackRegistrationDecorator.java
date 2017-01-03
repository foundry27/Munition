package pw.stamina.munition.event.core.registration;

import pw.stamina.munition.event.core.selection.Selector;

/**
 * @author Mark Johnson
 */
public class CancellationCallbackRegistrationDecorator<K, T> implements Registration<K ,T> {

    private final Registration<K, T> backingRegistration;

    private final Runnable cancellationCallback;

    public CancellationCallbackRegistrationDecorator(final Registration<K, T> backingRegistration, final Runnable cancellationCallback) {
       this.backingRegistration = backingRegistration;
       this.cancellationCallback = cancellationCallback;
    }

    @Override
    public void resume() {
        backingRegistration.resume();
    }

    @Override
    public void pause() {
        backingRegistration.pause();
    }

    @Override
    public void cancel() {
        if (!isCancelled()) {
            cancellationCallback.run();
        }
        backingRegistration.cancel();
    }

    @Override
    public Selector<K> getSelector() {
        return backingRegistration.getSelector();
    }

    @Override
    public T getObject() {
        return backingRegistration.getObject();
    }

    @Override
    public boolean isPaused() {
        return backingRegistration.isPaused();
    }

    @Override
    public boolean isCancelled() {
        return backingRegistration.isCancelled();
    }
}
