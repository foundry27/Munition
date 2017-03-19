package pw.stamina.munition.management.manifest;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mark Johnson
 */
public abstract class AbstractDeferringMutableManifest<T> implements MutableManifest<T> {
    
    protected abstract Stream<T> generateBackingStream();
    
    protected abstract MutableManifest<T> generateManifestStage(Supplier<Stream<T>> streamSupplier);

    @Override
    public abstract void evict();

    @Override
    public abstract void register(T entry);

    @Override
    public abstract void remove(T entry);

    @Override
    public ImmutableManifest<T> toImmutable() {
        return new ImmutableMutableManifestDecorator<>(this);
    }

    @Override
    public MutableManifest<T> filter(final Predicate<? super T> predicate) {
        return generateManifestStage(() -> generateBackingStream().filter(predicate));
    }

    @Override
    public MutableManifest<T> distinct() {
        return generateManifestStage(() -> generateBackingStream().distinct());
    }

    @Override
    public MutableManifest<T> sorted() {
        return generateManifestStage(() -> generateBackingStream().sorted());
    }

    @Override
    public MutableManifest<T> sorted(final Comparator<? super T> comparator) {
        return generateManifestStage(() -> generateBackingStream().sorted(comparator));
    }

    @Override
    public MutableManifest<T> peek(final Consumer<? super T> action) {
        return generateManifestStage(() -> generateBackingStream().peek(action));
    }

    @Override
    public MutableManifest<T> limit(final long maxSize) {
        return generateManifestStage(() -> generateBackingStream().limit(maxSize));
    }

    @Override
    public MutableManifest<T> skip(final long n) {
        return generateManifestStage(() -> generateBackingStream().skip(n));
    }

    @Override
    public Iterator<T> iterator() {
        return generateBackingStream().iterator();
    }

    @Override
    public void forEach(final Consumer<? super T> action) {
        generateBackingStream().forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return generateBackingStream().spliterator();
    }

    @Override
    public void forEachOrdered(final Consumer<? super T> action) {
        generateBackingStream().forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return generateBackingStream().toArray();
    }

    @Override
    public <A> A[] toArray(final IntFunction<A[]> generator) {
        return generateBackingStream().toArray(generator);
    }

    @Override
    public T reduce(final T identity, final BinaryOperator<T> accumulator) {
        return generateBackingStream().reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(final BinaryOperator<T> accumulator) {
        return generateBackingStream().reduce(accumulator);
    }

    @Override
    public <U> U reduce(final U identity, final BiFunction<U, ? super T, U> accumulator, final BinaryOperator<U> combiner) {
        return generateBackingStream().reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(final Supplier<R> supplier, final BiConsumer<R, ? super T> accumulator, final BiConsumer<R, R> combiner) {
        return generateBackingStream().collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(final Collector<? super T, A, R> collector) {
        return generateBackingStream().collect(collector);
    }

    @Override
    public Optional<T> min(final Comparator<? super T> comparator) {
        return generateBackingStream().min(comparator);
    }

    @Override
    public Optional<T> max(final Comparator<? super T> comparator) {
        return generateBackingStream().max(comparator);
    }

    @Override
    public long count() {
        return generateBackingStream().count();
    }

    @Override
    public boolean anyMatch(final Predicate<? super T> predicate) {
        return generateBackingStream().anyMatch(predicate);
    }

    @Override
    public boolean allMatch(final Predicate<? super T> predicate) {
        return generateBackingStream().allMatch(predicate);
    }

    @Override
    public boolean noneMatch(final Predicate<? super T> predicate) {
        return generateBackingStream().noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return generateBackingStream().findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return generateBackingStream().findAny();
    }

    @Override
    public String toString() {
        return generateBackingStream()
                .map(Object::toString)
                .collect(Collectors.joining(", ", "DeferringManifest{", "}"));
    }
}
