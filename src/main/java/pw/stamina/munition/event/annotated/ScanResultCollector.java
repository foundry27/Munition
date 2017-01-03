package pw.stamina.munition.event.annotated;

import pw.stamina.munition.event.core.Event;
import pw.stamina.munition.event.core.registration.Registration;
import pw.stamina.munition.event.core.scanning.ScanResult;
import pw.stamina.munition.event.core.scanning.ScanResultBuilder;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author Mark Johnson
 */
public class ScanResultCollector<K, E> implements Collector<Registration<? extends K, BiConsumer<K, Event<E>>>, ScanResultBuilder<K, Event<E>>, ScanResult<K, Event<E>>> {

    @Override
    public Supplier<ScanResultBuilder<K, Event<E>>> supplier() {
        return ScanResultBuilder::new;
    }

    @Override
    public BiConsumer<ScanResultBuilder<K, Event<E>>, Registration<? extends K, BiConsumer<K, Event<E>>>> accumulator() {
        return ScanResultBuilder::addCompletedRegistration;
    }

    @Override
    public BinaryOperator<ScanResultBuilder<K, Event<E>>> combiner() {
        return (left, right) -> {
            final ScanResult<K, Event<E>> rightResult = right.build();
            rightResult.getCompletedRegistrations().forEach(left::addCompletedRegistration);
            return left;
        };
    }

    @Override
    public Function<ScanResultBuilder<K, Event<E>>, ScanResult<K, Event<E>>> finisher() {
        return ScanResultBuilder::build;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}