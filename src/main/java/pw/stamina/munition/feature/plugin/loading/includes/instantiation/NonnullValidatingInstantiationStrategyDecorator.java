package pw.stamina.munition.feature.plugin.loading.includes.instantiation;

import java.util.Objects;

/**
 * @author Mark Johnson
 */
public final class NonnullValidatingInstantiationStrategyDecorator<T> implements InstantiationStrategy<T> {

    private final InstantiationStrategy<T> backingStrategy;

    public NonnullValidatingInstantiationStrategyDecorator(final InstantiationStrategy<T> backingStrategy) {
        this.backingStrategy = Objects.requireNonNull(backingStrategy, "The backing instantiation strategy cannot be null");
    }

    @Override
    public T instantiate(final Class<? extends T> targetClass) throws ObjectInstantiationException {
        if (targetClass == null) {
            throw new NullPointerException("The target class to be instantiated cannot be null");
        } else {
            return backingStrategy.instantiate(targetClass);
        }
    }
}
