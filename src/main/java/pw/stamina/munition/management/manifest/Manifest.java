package pw.stamina.munition.management.manifest;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Mark Johnson
 */
public interface Manifest<T> extends StreamLike<T, MutableManifest<T>>, Iterable<T> {
    <R extends T> Manifest<R> mapDown(Function<? super T, ? extends R> mapper);

    void forEach(final Consumer<? super T> action);
}
