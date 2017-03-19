package pw.stamina.munition.management.manifest;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Mark Johnson
 */
public interface ImmutableManifest<T> extends StreamLike<T, ImmutableManifest<T>>, Iterable<T> {
    <R extends T> ImmutableManifest<R> mapDown(Function<? super T, ? extends R> mapper);

    void forEach(final Consumer<? super T> action);
}
