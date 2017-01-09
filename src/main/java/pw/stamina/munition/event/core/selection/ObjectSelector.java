package pw.stamina.munition.event.core.selection;

import java.util.Objects;

/**
 * @author Mark Johnson
 */
public class ObjectSelector<K> extends AbstractUnarySelector<K> {

    public ObjectSelector(final K key) {
        super(key);
    }

    @Override
    public boolean canSelect(final K k) {
        return Objects.equals(key, k);
    }
}
