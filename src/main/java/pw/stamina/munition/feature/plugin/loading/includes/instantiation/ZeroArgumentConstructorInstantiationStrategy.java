package pw.stamina.munition.feature.plugin.loading.includes.instantiation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author Mark Johnson
 */
public final class ZeroArgumentConstructorInstantiationStrategy<T> implements InstantiationStrategy<T> {

    private static final ZeroArgumentConstructorInstantiationStrategy<?> INSTANCE = new ZeroArgumentConstructorInstantiationStrategy<>();

    private ZeroArgumentConstructorInstantiationStrategy() {}

    @Override
    public T instantiate(final Class<? extends T> targetClass) throws ObjectInstantiationException {
        try {
            final Constructor<? extends T> constructor = targetClass.getDeclaredConstructor();
            AccessController.doPrivileged((PrivilegedAction<Void>)() -> {
                constructor.setAccessible(true);
                return null;
            });
            return constructor.newInstance();
        } catch (final NoSuchMethodException e) {
            throw new ObjectInstantiationException("No zero-argument constructor exists for class " + targetClass.getCanonicalName());
        } catch (final IllegalAccessException e) {
            throw new ObjectInstantiationException("Unable to access constructor of class " + targetClass.getCanonicalName(), e);
        } catch (final InstantiationException e) {
            throw new ObjectInstantiationException(e);
        } catch (final InvocationTargetException e) {
            throw new ObjectInstantiationException("Constructor of class " + targetClass.getCanonicalName() + " threw an exception:", e);
        }
    }
    @SuppressWarnings("unchecked")
    public static <T> ZeroArgumentConstructorInstantiationStrategy<T> getInstance() {
        return ((ZeroArgumentConstructorInstantiationStrategy<T>) INSTANCE);
    }
}
