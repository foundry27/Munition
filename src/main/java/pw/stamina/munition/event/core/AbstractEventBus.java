package pw.stamina.munition.event.core;

import pw.stamina.munition.event.core.registration.Registration;
import pw.stamina.munition.event.core.registration.registry.Registry;
import pw.stamina.munition.event.core.routing.Router;
import pw.stamina.munition.event.core.selection.Selector;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Mark Johnson
 */
public abstract class AbstractEventBus<K, E> implements EventBus<K, E> {

    private final Registry<K, BiConsumer<K, E>> consumerRegistry;

    private final Router<K, E> router;

    protected AbstractEventBus(final Registry<K, BiConsumer<K, E>> consumerRegistry, final Router<K, E> router) {
        this.consumerRegistry = Objects.requireNonNull(consumerRegistry, "The consumer registry cannot be null");
        this.router = Objects.requireNonNull(router, "The router cannot be null");
    }

    @Override
    public boolean respondsToKey(final K key) {
        return consumerRegistry.select(key)
                .anyMatch(registration -> !registration.isCancelled());
    }

    @Override
    public Registration<? extends K, BiConsumer<K, E>> on(final Selector<? extends K> selector, final BiConsumer<K, E> keyAndEventConsumer) {
        return consumerRegistry.register(selector, keyAndEventConsumer);
    }

    @Override
    public Registration<? extends K, BiConsumer<K, E>> on(final Selector<? extends K> selector, final Consumer<E> eventConsumer) {
        return consumerRegistry.register(selector, (key, event) -> eventConsumer.accept(event));
    }

    @Override
    public EventBus<K, E> notify(final K key, final E event) {
        router.route(key, event, consumerRegistry.select(key));
        return this;
    }
}
