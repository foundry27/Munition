package pw.stamina.munition.event.core.selection;

/**
 * @author Mark Johnson
 */
public class ObjectSelector<K> extends AbstractUnarySelector<K> {

    public ObjectSelector(final K key) {
        super(key);
    }

    @Override
    public boolean canSelect(final K k) {
        return key.equals(k);
    }
}
