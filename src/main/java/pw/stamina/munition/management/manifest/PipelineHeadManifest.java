package pw.stamina.munition.management.manifest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Mark Johnson
 */
public class PipelineHeadManifest<T> extends AbstractDeferringManifest<T> implements Manifest<T> {

    private final Collection<T> backingCollection;

    public PipelineHeadManifest(final Collection<T> backingCollection) {
        this.backingCollection = Objects.requireNonNull(backingCollection, "The backing collection cannot be null");
    }

    @Override
    protected Stream<T> generateBackingStream() {
        return backingCollection.stream();
    }

    @Override
    protected Manifest<T> generateManifestStage(final Supplier<Stream<T>> streamSupplier) {
        return new PipelineNodeManifest<>(streamSupplier,
                stream -> {
                    final List<T> removalCandidates = stream.collect((Supplier<List<T>>) ArrayList::new, List::add, List::addAll);
                    backingCollection.removeAll(removalCandidates);
                },
                this::register,
                this::remove);
    }

    @Override
    public void evict() {
        backingCollection.clear();
    }

    @Override
    public void register(final T entry) {
        backingCollection.add(entry);
    }

    @Override
    public void remove(final T entry) {
        backingCollection.remove(entry);
    }

    @Override
    public <R extends T> Manifest<R> mapDown(final Function<? super T, ? extends R> mapper) {
        return new PipelineNodeManifest<>(() -> generateBackingStream().map(mapper),
                stream -> {
                    final List<T> removalCandidates = stream.collect((Supplier<List<T>>) ArrayList::new, List::add, List::addAll);
                    backingCollection.removeAll(removalCandidates);
                },
                this::register,
                this::remove);
    }
}
