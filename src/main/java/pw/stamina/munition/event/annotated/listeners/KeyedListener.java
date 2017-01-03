package pw.stamina.munition.event.annotated.listeners;

import pw.stamina.munition.event.core.Event;
import pw.stamina.munition.event.core.selection.Selector;

/**
 * @author Mark Johnson
 */
public class KeyedListener<K, E> implements KeyedListenerBody<K, E>, Selective<K> {

    private final KeyedListenerBody<K, E> keyedListenerBody;

    private final Selector<K> selector;

    public KeyedListener(final KeyedListenerBody<K, E> keyedListenerBody, final Selector<K> selector) {
        this.keyedListenerBody = keyedListenerBody;
        this.selector = selector;
    }

    @Override
    public Selector<K> getSelector() {
        return selector;
    }

    @Override
    public void accept(final K key, final Event<E> event) {
        keyedListenerBody.accept(key, event);
    }
}
