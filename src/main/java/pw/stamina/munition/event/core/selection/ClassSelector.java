package pw.stamina.munition.event.core.selection;

import java.util.Objects;

/**
 * @author Mark Johnson
 */
public class ClassSelector<K> extends AbstractBiSelector<K, Class<? extends K>> {

    public ClassSelector(final Class<? extends K> key) {
        super(Objects.requireNonNull(key, "Class for selector cannot be null"));
    }

    @Override
    public boolean canSelect(final K k) {
        return k != null && key.isAssignableFrom(k.getClass());
    }
}
