package pw.stamina.munition.event.core.scanning;

import pw.stamina.munition.event.core.registration.Registration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author Mark Johnson
 */
public class ScanResultBuilder<K, E> {

    private final List<Registration<? extends K, BiConsumer<K, E>>> completedRegistrations = new ArrayList<>();

    public ScanResultBuilder<K, E> addCompletedRegistration(final Registration<? extends K, BiConsumer<K, E>> registration) {
        completedRegistrations.add(registration);
        return this;
    }

    public ScanResult<K, E> build() {
        return new SimpleImmutableScanResult<K, E>(completedRegistrations);
    }
}
