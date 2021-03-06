package pw.stamina.munition.event.core.selection;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * @author Mark Johnson
 */
public final class Selectors {

    private static final Map<Class<?>, Function<Object, Selector<?>>> DYNAMIC_SELECTOR_FACTORIES;

    private Selectors() {}

    public static <K> Selector<K> $(final K key) {
        return new ObjectSelector<>(key);
    }

    public static <K> Selector<K> object(final K key) {
        return new ObjectSelector<>(key);
    }

    public static <K> Selector<K> when(final Predicate<K> key) {
        return new PredicateSelector<>(key);
    }

    public static <K extends CharSequence> Selector<K> regex(final String regex, final int flags) {
        return new RegexSelector<>(Pattern.compile(regex, flags));
    }

    public static <K extends CharSequence> Selector<K> regex(final String regex) {
        return new RegexSelector<>(Pattern.compile(Objects.requireNonNull(regex, "Regex string for selector cannot be null")));
    }

    public static <K> Selector<K> isA(final Class<? extends K> clazz) {
        return new ClassSelector<>(clazz);
    }

    public static <K> Selector<K> any() {
        return PassThroughSelector.getInstance();
    }

    public static <K> Selector<K> dynamic(final K key) {
        return key != null ? findAppropriateSelectorForNonnullObject(key) : any();
    }

    @SuppressWarnings("unchecked")
    private static <K> Selector<K> findAppropriateSelectorForNonnullObject(final K object) {
        for (Class<?> clazz = object.getClass(); clazz.getSuperclass() != null; clazz = clazz.getSuperclass()) {
            final Function<Object, Selector<?>> lookupFromClass = DYNAMIC_SELECTOR_FACTORIES.get(clazz);
            if (lookupFromClass == null) {
                for (final Map.Entry<Class<?>, Function<Object, Selector<?>>> factoryMapping : DYNAMIC_SELECTOR_FACTORIES.entrySet()) {
                    if (factoryMapping.getKey().isAssignableFrom(clazz)) {
                        return (Selector<K>) factoryMapping.getValue().apply(object);
                    }
                }
            } else {
                return (Selector<K>) lookupFromClass.apply(object);
            }
        }
        return $(object);
    }

    static {
        final Map<Class<?>, Function<Object, Selector<?>>> selectorFactories = new HashMap<>(3, 1.0f);

        selectorFactories.put(Class.class, clazz -> new ClassSelector<>((Class<?>) clazz));
        selectorFactories.put(Predicate.class, predicate -> new PredicateSelector<>((Predicate<?>) predicate));
        selectorFactories.put(Pattern.class, pattern -> new RegexSelector<>((Pattern) pattern));

        DYNAMIC_SELECTOR_FACTORIES = selectorFactories;
    }
}
