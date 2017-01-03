package pw.stamina.munition.event.core;

import pw.stamina.munition.event.core.scanning.ScanResult;

/**
 * @author Mark Johnson
 */
public interface ScanningEventBus<K, E, R> extends EventBus<K, E> {
    ScanResult<K, E> scan(R registrant) throws ScanFailedException;

    ScanningEventBus<K, E, R> notify(K key, E event);
}
