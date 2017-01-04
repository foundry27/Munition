package pw.stamina.munition.event.annotated;

import pw.stamina.munition.event.core.Event;
import pw.stamina.munition.event.core.ScanFailedException;
import pw.stamina.munition.event.core.registration.registry.Registry;
import pw.stamina.munition.event.core.routing.Router;
import pw.stamina.munition.event.core.scanning.ScanResult;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author Mark Johnson
 */
public class CachingAnnotatedEventBus<K, E, R> extends AbstractAnnotatedEventBus<K, Event<E>, R> {

    private final ConcurrentMap<R, List<Object>> fieldContentsCache = new ConcurrentHashMap<>();

    public CachingAnnotatedEventBus(final Registry<K, BiConsumer<K, Event<E>>> registrations, final Router<K, Event<E>> router) {
        super(registrations, router);
    }

    @Override
    public ScanResult<K, Event<E>> scan(final R registrant) throws ScanFailedException {
        return fieldContentsCache.computeIfAbsent(registrant, reg ->
                Arrays.stream(reg.getClass().getDeclaredFields())
                    .filter(CachingAnnotatedEventBus::checkIfValidFieldAndEnsureConsistency)
                    .map(field -> getFieldContents(field, reg))
                    .collect(Collectors.toList()))
                .stream()
                .map(this::tryCreatingRegistrationFromFieldContent)
                .collect(new ScanResultCollector<>());
    }
}
