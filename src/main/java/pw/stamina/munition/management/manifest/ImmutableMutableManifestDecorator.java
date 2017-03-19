package pw.stamina.munition.management.manifest;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.Collector;

/**
 * @author Mark Johnson
 */
public final class ImmutableMutableManifestDecorator<T> implements ImmutableManifest<T> {

    private final MutableManifest<T> backingManifest;

    public ImmutableMutableManifestDecorator(final MutableManifest<T> backingManifest) {
        this.backingManifest = backingManifest;
    }

    @Override
    public <R extends T> ImmutableManifest<R> mapDown(final Function<? super T, ? extends R> mapper) {
        return new ImmutableMutableManifestDecorator<>(backingManifest.mapDown(mapper));
    }

    @Override
    public Iterator<T> iterator() {
        return backingManifest.iterator();
    }

    @Override
    public void forEach(final Consumer<? super T> action) {
        backingManifest.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return backingManifest.spliterator();
    }

    @Override
    public void forEachOrdered(final Consumer<? super T> action) {
        backingManifest.forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return backingManifest.toArray();
    }

    @Override
    public <A> A[] toArray(final IntFunction<A[]> generator) {
        return backingManifest.toArray(generator);
    }

    @Override
    public T reduce(final T identity, final BinaryOperator<T> accumulator) {
        return backingManifest.reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(final BinaryOperator<T> accumulator) {
        return backingManifest.reduce(accumulator);
    }

    @Override
    public <U> U reduce(final U identity, final BiFunction<U, ? super T, U> accumulator, final BinaryOperator<U> combiner) {
        return backingManifest.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(final Supplier<R> supplier, final BiConsumer<R, ? super T> accumulator, final BiConsumer<R, R> combiner) {
        return backingManifest.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(final Collector<? super T, A, R> collector) {
        return backingManifest.collect(collector);
    }

    @Override
    public Optional<T> min(final Comparator<? super T> comparator) {
        return backingManifest.min(comparator);
    }

    @Override
    public Optional<T> max(final Comparator<? super T> comparator) {
        return backingManifest.max(comparator);
    }

    @Override
    public long count() {
        return backingManifest.count();
    }

    @Override
    public boolean anyMatch(final Predicate<? super T> predicate) {
        return backingManifest.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(final Predicate<? super T> predicate) {
        return backingManifest.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(final Predicate<? super T> predicate) {
        return backingManifest.noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return backingManifest.findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return backingManifest.findAny();
    }

    @Override
    public ImmutableManifest<T> filter(final Predicate<? super T> predicate) {
        return new ImmutableMutableManifestDecorator<>(backingManifest.filter(predicate));
    }

    @Override
    public ImmutableManifest<T> distinct() {
        return new ImmutableMutableManifestDecorator<>(backingManifest.distinct());
    }

    @Override
    public ImmutableManifest<T> sorted() {
        return new ImmutableMutableManifestDecorator<>(backingManifest.sorted());
    }

    @Override
    public ImmutableManifest<T> sorted(final Comparator<? super T> comparator) {
        return new ImmutableMutableManifestDecorator<>(backingManifest.sorted(comparator));
    }

    @Override
    public ImmutableManifest<T> peek(final Consumer<? super T> action) {
        return new ImmutableMutableManifestDecorator<>(backingManifest.peek(action));
    }

    @Override
    public ImmutableManifest<T> limit(final long maxSize) {
        return new ImmutableMutableManifestDecorator<>(backingManifest.limit(maxSize));
    }

    @Override
    public ImmutableManifest<T> skip(final long n) {
        return new ImmutableMutableManifestDecorator<>(backingManifest.skip(n));
    }
}
