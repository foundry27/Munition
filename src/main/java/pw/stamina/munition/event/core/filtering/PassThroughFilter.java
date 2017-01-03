package pw.stamina.munition.event.core.filtering;

import java.util.stream.Stream;

/**
 * @author Mark Johnson
 */
public class PassThroughFilter<T, K> implements KeyedFilter<T, K> {
    @Override
    public Stream<T> filter(final Stream<T> input, final K key) {
        return input;
    }
}
