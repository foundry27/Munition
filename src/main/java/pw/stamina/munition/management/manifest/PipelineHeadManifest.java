package pw.stamina.munition.management.manifest;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mark Johnson
 */
public class PipelineHeadManifest<T> extends AbstractDeferringManifest<T> implements Manifest<T> {

    private final Collection<T> featureCollection;

    public PipelineHeadManifest(final Collection<T> featureCollection) {
        this.featureCollection = featureCollection;
    }

    @Override
    protected Stream<T> generateBackingStream() {
        return featureCollection.stream();
    }

    @Override
    protected Manifest<T> generateManifestStage(final Supplier<Stream<T>> streamSupplier) {
        return new PipelineNodeManifest<>(streamSupplier,
                stream -> featureCollection.removeAll(stream.collect(Collectors.toList())),
                this::register,
                this::remove);
    }

    @Override
    public void evict() {
        featureCollection.clear();
    }

    @Override
    public void register(final T entry) {
        featureCollection.add(entry);
    }

    @Override
    public void remove(final T entry) {
        featureCollection.remove(entry);
    }

    @Override
    public <R extends T> Manifest<R> mapDown(final Function<? super T, ? extends R> mapper) {
        return new PipelineNodeManifest<>(() -> generateBackingStream().map(mapper),
                stream -> featureCollection.removeAll(stream.collect(Collectors.toList())),
                this::register,
                this::remove);
    }
}
