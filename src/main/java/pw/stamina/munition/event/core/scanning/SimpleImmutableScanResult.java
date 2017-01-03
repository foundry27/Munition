package pw.stamina.munition.event.core.scanning;

import pw.stamina.munition.event.core.registration.Registration;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author Mark Johnson
 */
public class SimpleImmutableScanResult<K, E> implements ScanResult<K, E> {

    private final List<Registration<? extends K, BiConsumer<K, E>>> completedRegistrations;

    public SimpleImmutableScanResult(final List<Registration<? extends K, BiConsumer<K, E>>> completedRegistrations) {
        this.completedRegistrations = Collections.unmodifiableList(completedRegistrations);
    }

    @Override
    public List<Registration<? extends K, BiConsumer<K, E>>> getCompletedRegistrations() {
        return completedRegistrations;
    }

}
