package pw.stamina.munition.management.manifest;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface StreamLike<T, S extends StreamLike<T, S>> extends ConsumableLike<T> {
    S filter(Predicate<? super T> predicate);

    S distinct();

    S sorted();

    S sorted(Comparator<? super T> comparator);

    S peek(Consumer<? super T> action);

    S limit(long maxSize);

    S skip(long n);
}
