package pw.stamina.munition.event.core.selection;

/**
 * @author Mark Johnson
 */
public abstract class AbstractUnarySelector<K> extends AbstractBiSelector<K, K> {
    protected AbstractUnarySelector(final K key) {
        super(key);
    }
}
