package pw.stamina.munition.event.core.scanning;

import pw.stamina.munition.event.core.registration.Registration;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author Mark Johnson
 */
public interface ScanResult<K, E> {
    List<Registration<? extends K, BiConsumer<K, E>>> getCompletedRegistrations();
}
