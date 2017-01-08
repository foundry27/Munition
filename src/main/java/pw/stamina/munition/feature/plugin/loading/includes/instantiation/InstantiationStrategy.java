package pw.stamina.munition.feature.plugin.loading.includes.instantiation;

/**
 * @author Mark Johnson
 */
public interface InstantiationStrategy<T> {
    T instantiate(final Class<? extends T> targetClass) throws ObjectInstantiationException;
}
