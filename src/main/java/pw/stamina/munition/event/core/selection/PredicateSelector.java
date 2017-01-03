package pw.stamina.munition.event.core.selection;

import java.util.function.Predicate;

/**
 * @author Mark Johnson
 */
public class PredicateSelector<K> extends AbstractBiSelector<K, Predicate<K>> {

    public PredicateSelector(final Predicate<K> key) {
        super(key);
    }

    @Override
    public boolean canSelect(final K k) {
        return key.test(k);
    }
}
