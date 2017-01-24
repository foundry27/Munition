package pw.stamina.munition.management.manifest;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * @author Mark Johnson
 */
public final class PipelineNodeMutableManifest<T, S extends PipelineNodeMutableManifest<T, S>> extends AbstractDeferringMutableManifest<T> implements MutableManifest<T> {

    private final Supplier<Stream<T>> streamSupplier;

    private final Consumer<Stream<? extends T>> evictionStrategy;

    private final Consumer<T> registrationStrategy;

    private final Consumer<T> removalStrategy;

    public PipelineNodeMutableManifest(final Supplier<Stream<T>> streamSupplier,
                                       final Consumer<Stream<? extends T>> evictionStrategy,
                                       final Consumer<T> registrationStrategy,
                                       final Consumer<T> removalStrategy) {
        this.streamSupplier = streamSupplier;
        this.evictionStrategy = evictionStrategy;
        this.registrationStrategy = registrationStrategy;
        this.removalStrategy = removalStrategy;
    }

    @Override
    protected Stream<T> generateBackingStream() {
        return streamSupplier.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected S generateManifestStage(final Supplier<Stream<T>> streamSupplier) {
        return (S) new PipelineNodeMutableManifest<T, S>(streamSupplier, evictionStrategy, registrationStrategy, removalStrategy);
    }

    @Override
    public void evict() {
        evictionStrategy.accept(streamSupplier.get());
    }

    @Override
    public void register(final T entry) {
        removalStrategy.accept(entry);
    }

    @Override
    public void remove(final T entry) {
        removalStrategy.accept(entry);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R extends T> MutableManifest<R> mapDown(final Function<? super T, ? extends R> mapper) {
        return new PipelineNodeMutableManifest<>(() -> streamSupplier.get().map(mapper),
                (Consumer<Stream<? extends R>>) evictionStrategy::accept,
                (Consumer<R>) registrationStrategy,
                (Consumer<R>) removalStrategy);
    }

    @Override
    public void forEach(final Consumer<? super T> action) {
        streamSupplier.get().forEach(action);
    }

    @Override
    public void forEachOrdered(final Consumer<? super T> action) {
        streamSupplier.get().forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return streamSupplier.get().toArray();
    }

    @Override
    public <A> A[] toArray(final IntFunction<A[]> generator) {
        return streamSupplier.get().toArray(generator);
    }

    @Override
    public T reduce(final T identity, final BinaryOperator<T> accumulator) {
        return streamSupplier.get().reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(final BinaryOperator<T> accumulator) {
        return streamSupplier.get().reduce(accumulator);
    }

    @Override
    public <U> U reduce(final U identity, final BiFunction<U, ? super T, U> accumulator, final BinaryOperator<U> combiner) {
        return streamSupplier.get().reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(final Supplier<R> supplier, final BiConsumer<R, ? super T> accumulator, final BiConsumer<R, R> combiner) {
        return streamSupplier.get().collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(final Collector<? super T, A, R> collector) {
        return streamSupplier.get().collect(collector);
    }

    @Override
    public Optional<T> min(final Comparator<? super T> comparator) {
        return streamSupplier.get().min(comparator);
    }

    @Override
    public Optional<T> max(final Comparator<? super T> comparator) {
        return streamSupplier.get().max(comparator);
    }

    @Override
    public long count() {
        return streamSupplier.get().count();
    }

    @Override
    public boolean anyMatch(final Predicate<? super T> predicate) {
        return streamSupplier.get().anyMatch(predicate);
    }

    @Override
    public boolean allMatch(final Predicate<? super T> predicate) {
        return streamSupplier.get().allMatch(predicate);
    }

    @Override
    public boolean noneMatch(final Predicate<? super T> predicate) {
        return streamSupplier.get().noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return streamSupplier.get().findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return streamSupplier.get().findAny();
    }
}
