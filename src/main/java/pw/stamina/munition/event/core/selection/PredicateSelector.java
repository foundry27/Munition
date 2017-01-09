package pw.stamina.munition.event.core.selection;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Mark Johnson
 */
public class PredicateSelector<K> extends AbstractBiSelector<K, Predicate<K>> {

    public PredicateSelector(final Predicate<K> key) {
        super(Objects.requireNonNull(key, "Predicate for selector cannot be null"));
    }

    @Override
    public boolean canSelect(final K k) {
        return key.test(k);
    }
}
