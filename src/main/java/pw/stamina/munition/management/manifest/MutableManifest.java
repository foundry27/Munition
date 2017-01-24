package pw.stamina.munition.management.manifest;

import java.util.function.Function;

/**
 * @author Mark Johnson
 */
public interface MutableManifest<T> extends Manifest<T> {
    void evict();

    void register(T entry);

    void remove(T entry);

    <R extends T> MutableManifest<R> mapDown(Function<? super T, ? extends R> mapper);
}