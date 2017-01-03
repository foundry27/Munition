package pw.stamina.munition.event.annotated.listeners;

import pw.stamina.munition.event.core.Event;
import pw.stamina.munition.event.core.selection.Selector;

/**
 * @author Mark Johnson
 */
public class Listener<K, E> implements ListenerBody<E>, Selective<K> {

    private final ListenerBody<E> listenerBody;

    private final Selector<K> selector;

    public Listener(final ListenerBody<E> listenerBody, final Selector<K> selector) {
        this.listenerBody = listenerBody;
        this.selector = selector;
    }

    @Override
    public void accept(final Event<E> event) {
        listenerBody.accept(event);
    }

    @Override
    public Selector<K> getSelector() {
        return selector;
    }
}
