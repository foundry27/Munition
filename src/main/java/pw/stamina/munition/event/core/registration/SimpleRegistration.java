package pw.stamina.munition.event.core.registration;

import pw.stamina.munition.event.core.selection.Selector;

/**
 * @author Mark Johnson
 */
public class SimpleRegistration<K, T> extends AbstractRegistration<K, T> {

    private final Selector<K> selector;

    private final T object;

    public SimpleRegistration(final Selector<K> selector, final T object) {
        this.selector = selector;
        this.object = object;
    }

    @Override
    public Selector<K> getSelector() {
        return selector;
    }

    @Override
    public T getObject() {
        return object;
    }
}
