package pw.stamina.munition.event.core.selection;

/**
 * @author Mark Johnson
 */
public final class PassThroughSelector<K> implements Selector<K> {

    private static final PassThroughSelector<?> INSTANCE = new PassThroughSelector<>();

    private PassThroughSelector() {}

    @SuppressWarnings("unchecked")
    public static <K> PassThroughSelector<K> getInstance() {
        return (PassThroughSelector<K>) INSTANCE;
    }

    @Override
    public boolean canSelect(final Object k) {
        return true;
    }
}
