package pw.stamina.munition.event.annotated.method;

import pw.stamina.munition.event.annotated.Reactive;
import pw.stamina.munition.event.annotated.ScanResultCollector;
import pw.stamina.munition.event.core.AbstractScanningEventBus;
import pw.stamina.munition.event.core.Event;
import pw.stamina.munition.event.core.ScanFailedException;
import pw.stamina.munition.event.core.registration.Registration;
import pw.stamina.munition.event.core.registration.registry.Registry;
import pw.stamina.munition.event.core.routing.Router;
import pw.stamina.munition.event.core.scanning.ScanResult;
import pw.stamina.munition.event.core.selection.AbstractBiSelector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Mark Johnson
 */
public class SimpleAnnotatedMethodEventBus<K, E, R> extends AbstractScanningEventBus<Class<? extends K>, Event<E>, R> {

    private final Class<K> baseEventClass;

    public SimpleAnnotatedMethodEventBus(final Class<K> baseEventClass, final Registry<Class<? extends K>, BiConsumer<Class<? extends K>, Event<E>>> registrations, final Router<Class<? extends K>, Event<E>> router) {
        super(registrations, router);
        this.baseEventClass = baseEventClass;
    }

    @Override
    public ScanResult<Class<? extends K>, Event<E>> scan(final R registrant) throws ScanFailedException {
        return Arrays.stream(registrant.getClass().getDeclaredMethods())
                .filter(this::ensureIsValidMethod)
                .map(method -> createRegistration(method, registrant))
                .collect(new ScanResultCollector<>());
    }

    private boolean ensureIsValidMethod(final Method method) {
        if (!method.isAnnotationPresent(Reactive.class)) {
            return false;
        } else {
            switch (method.getParameterCount()) {
                case 1: checkThatConsumerMethodIsValid(method);
                    break;
                default: throw new ScanFailedException(String.format("Reactive-annotated method %s must have only one parameter", method.getName()));
            }
        }
        return true;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Registration<? extends Class<? extends K>, BiConsumer<Class<? extends K>, Event<E>>> createRegistration(final Method method, final Object parent) {
        return on(new DirectClassSelector<>((Class) ensureTypeIsEventAndResolveEventType(method.getGenericParameterTypes()[0])),
                new ReflectiveMethodInvokerEventConsumer<>(method, parent));
    }

    private void checkThatConsumerMethodIsValid(final Method method) {
        final Class<?> eventType = ensureTypeIsEventAndResolveEventType(method.getGenericParameterTypes()[0]);
        if (!baseEventClass.isAssignableFrom(eventType)) {
            throw new ScanFailedException(String.format("Reactive-annotated method %s does not have a first parameter compatible with classes of type %s",
                    method.getName(), baseEventClass.getCanonicalName()));
        }
    }

    private static Class<?> ensureTypeIsEventAndResolveEventType(final Type type) {
        if (!(type instanceof ParameterizedType)) {
            throw new ScanFailedException(String.format("Type %s is not of type %s", type.getTypeName(), Event.class.getCanonicalName()));
        }
        final ParameterizedType parameterizedType = (ParameterizedType) type;
        if (!Event.class.equals(parameterizedType.getRawType())) {
            throw new ScanFailedException(String.format("Type %s is not of type %s", type.getTypeName(), Event.class.getCanonicalName()));
        }
        return (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }

    private static class ReflectiveMethodInvokerEventConsumer<E> implements Consumer<E> {

        private final Method method;

        private final Object parent;

        ReflectiveMethodInvokerEventConsumer(final Method method, final Object parent) {
            this.method = method;
            AccessController.doPrivileged((PrivilegedAction<Void>)() -> {
                this.method.setAccessible(true);
                return null;
            });
            this.parent = parent;
        }

        @Override
        public void accept(final E event) {
            try {
                method.invoke(parent, event);
            } catch (final IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(String.format("Exception encountered while executing reflected method %s in class %s:",
                        method.getName(), method.getDeclaringClass().getCanonicalName()), e);
            }
        }
    }

    private static class DirectClassSelector<K> extends AbstractBiSelector<Class<K>, Class<? extends K>> {

        DirectClassSelector(final Class<? extends K> key) {
            super(Objects.requireNonNull(key, "Class for selector cannot be null"));
        }

        @Override
        public boolean canSelect(final Class<K> k) {
            return k != null && key.isAssignableFrom(k);
        }
    }
}
