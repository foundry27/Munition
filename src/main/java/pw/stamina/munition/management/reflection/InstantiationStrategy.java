package pw.stamina.munition.management.reflection;

/**
 * @author Mark Johnson
 */
public interface InstantiationStrategy<T> {
    T instantiate(final Class<? extends T> targetClass) throws ObjectInstantiationException;
}
