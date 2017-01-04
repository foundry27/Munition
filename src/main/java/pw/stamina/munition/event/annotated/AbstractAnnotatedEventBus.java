package pw.stamina.munition.event.annotated;

import pw.stamina.munition.event.annotated.listeners.KeyedListener;
import pw.stamina.munition.event.annotated.listeners.Listener;
import pw.stamina.munition.event.core.AbstractScanningEventBus;
import pw.stamina.munition.event.core.ScanFailedException;
import pw.stamina.munition.event.core.registration.Registration;
import pw.stamina.munition.event.core.registration.registry.Registry;
import pw.stamina.munition.event.core.routing.Router;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Mark Johnson
 */
public abstract class AbstractAnnotatedEventBus<K, E, R> extends AbstractScanningEventBus<K, E, R> {

    protected AbstractAnnotatedEventBus(final Registry<K, BiConsumer<K, E>> registrations, final Router<K, E> router) {
        super(registrations, router);
    }

    protected final Object getFieldContents(final Field field, final R registrant) {
        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            field.setAccessible(true);
            return null;
        });
        try {
            return field.get(registrant);
        } catch (final IllegalAccessException e) {
            throw new ScanFailedException(e);
        }
    }

    @SuppressWarnings("unchecked")
    protected final Registration<? extends K, BiConsumer<K, E>> tryCreatingRegistrationFromFieldContent(final Object fieldContents) {
        if (fieldContents instanceof Listener) {
            return on(((Listener<K, E>) fieldContents).getSelector(), ((Consumer<E>) fieldContents));
        } else {
            return on(((KeyedListener<K, E>) fieldContents).getSelector(), ((BiConsumer<K, E>) fieldContents));
        }
    }

    protected final static boolean checkIfValidFieldAndEnsureConsistency(final Field field) {
        if (!field.isAnnotationPresent(Reactive.class)) {
            return false;
        } else {
            if (!Listener.class.isAssignableFrom(field.getType()) && !KeyedListener.class.isAssignableFrom(field.getType())) {
                throw new ScanFailedException(String.format("@Reactive-annotated field must have a type / supertype of one of {%s}",
                        String.join(", ", Listener.class.getCanonicalName(), KeyedListener.class.getCanonicalName())));
            }
            if (!Modifier.isFinal(field.getModifiers())) {
                throw new ScanFailedException("@Reactive-annotated fields must be marked as final to be registered");
            }
        }
        return true;
    }

}
