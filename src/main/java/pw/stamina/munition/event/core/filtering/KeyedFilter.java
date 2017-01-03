package pw.stamina.munition.event.core.filtering;

import java.util.stream.Stream;

/**
 * @author Mark Johnson
 */
public interface KeyedFilter<T, K> {
    Stream<T> filter(Stream<T> input, K key);
}
