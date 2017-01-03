package pw.stamina.munition.event.core.selection;

/**
 * @author Mark Johnson
 */
public class ClassSelector<K> extends AbstractBiSelector<K, Class<? extends K>> {

    public ClassSelector(final Class<? extends K> selectorClass) {
        super(selectorClass);
    }

    @Override
    public boolean canSelect(final K k) {
        return key.isAssignableFrom(k.getClass());
    }
}
