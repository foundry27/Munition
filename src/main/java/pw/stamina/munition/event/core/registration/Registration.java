package pw.stamina.munition.event.core.registration;

import pw.stamina.munition.core.traits.Cancellable;
import pw.stamina.munition.core.traits.Pausable;
import pw.stamina.munition.event.core.selection.Selector;

/**
 * @author Mark Johnson
 */
public interface Registration<K, T> extends Pausable, Cancellable {
    Selector<K> getSelector();

    T getObject();

    boolean isPaused();

    boolean isCancelled();
}
