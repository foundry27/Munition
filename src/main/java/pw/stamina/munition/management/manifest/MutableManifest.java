package pw.stamina.munition.management.manifest;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Mark Johnson
 */
public interface MutableManifest<T> extends StreamLike<T, MutableManifest<T>>, Iterable<T> {
    void forEach(final Consumer<? super T> action);

    void evict();

    void register(T entry);

    void remove(T entry);

    <R extends T> MutableManifest<R> mapDown(Function<? super T, ? extends R> mapper);

    ImmutableManifest<T> toImmutable();
}