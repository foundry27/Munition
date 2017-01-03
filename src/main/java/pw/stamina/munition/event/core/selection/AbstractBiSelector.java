package pw.stamina.munition.event.core.selection;

/**
 * @author Mark Johnson
 */
public abstract class AbstractBiSelector<K, T> implements Selector<K> {

    protected final T key;

    protected AbstractBiSelector(final T key) {
        this.key = key;
    }
}
