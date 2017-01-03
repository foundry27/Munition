package pw.stamina.munition.event.core.registration.registry;

import pw.stamina.munition.event.core.registration.CancellationCallbackRegistrationDecorator;
import pw.stamina.munition.event.core.registration.Registration;
import pw.stamina.munition.event.core.registration.SimpleRegistration;
import pw.stamina.munition.event.core.selection.Selector;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

/**
 * @author Mark Johnson
 */
public class SimpleRegistry<K, V> implements Registry<K, V> {

    private final ConcurrentMap<Selector<? extends K>, List<Registration<? extends K, V>>> registrations = new ConcurrentHashMap<>();

    @Override
    public Registration<? extends K, V> register(final Selector<? extends K> selector, final V object) {
        final Registration<? extends K, V> registration = new CancellationCallbackRegistrationDecorator<>(
                new SimpleRegistration<>(selector, object), () -> registrations.remove(selector));
        registrations.computeIfAbsent(selector, x -> new ArrayList<>()).add(registration);
        return registration;
    }

    @Override
    public boolean unregister(final K key) {
        return registrations.keySet().removeIf(selector -> ((Selector<K>) selector).canSelect(key));
    }

    @Override
    public Stream<Registration<? extends K, V>> select(final K key) {
        return registrations.entrySet().stream()
                .filter(entry -> ((Selector<K>) entry.getKey()).canSelect(key))
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream);
    }

    @Override
    public void clear() {
        registrations.clear();
    }

    @Override
    public long size() {
        return registrations.size();
    }

    @Override
    public Iterator<Registration<? extends K, V>> iterator() {
        return registrations.values().stream()
                .flatMap(Collection::stream)
                .iterator();
    }
}
