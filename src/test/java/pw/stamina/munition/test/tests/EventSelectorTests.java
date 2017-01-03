package pw.stamina.munition.test.tests;

import org.junit.Test;
import pw.stamina.munition.event.core.Event;
import pw.stamina.munition.event.core.EventBus;
import pw.stamina.munition.event.core.registration.Registration;
import pw.stamina.munition.event.core.registration.registry.SimpleRegistry;
import pw.stamina.munition.event.core.routing.SimpleRouter;
import pw.stamina.munition.event.core.selection.*;
import pw.stamina.munition.event.direct.SimpleEventBus;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pw.stamina.munition.event.core.selection.Selectors.$;
import static pw.stamina.munition.event.core.selection.Selectors.dynamic;

/**
 * @author Mark Johnson
 */
public class EventSelectorTests {
    @Test
    public void testUsingUniqueObjectWithObjectSelector() {
        final EventBus<Object, Event<Object>> bus = new SimpleEventBus<>(new SimpleRegistry<>(), new SimpleRouter<>());

        final Object testObjectKey = new Object();

        final Registration<?, ?> reg = bus.on($(testObjectKey), event -> {});

        assertTrue("Event bus did not respond to key matching registered object", bus.respondsToKey(testObjectKey));
    }

    @Test
    public void testUsingRegexWithRegexSelector() {
        final EventBus<Object, Event<Object>> bus = new SimpleEventBus<>(new SimpleRegistry<>(), new SimpleRouter<>());
        final String atLeasOneIntegerMatcherPattern = "^[0-9]+$";
        final String successfulKey = "123456";
        final String failingKey = "abcdefg123456";

        final Registration<?, ?> reg = bus.on(new RegexSelector<>(atLeasOneIntegerMatcherPattern), event -> {});

        assertTrue("Event bus did not respond to key matching registered object", bus.respondsToKey(successfulKey));
        assertFalse("Event bus responded to invalid key", bus.respondsToKey(failingKey));
    }

    @Test
    public void testDynamicallyGeneratingMostAppropriateSelector() {
        final Object plainObject = "POJO";
        final Pattern regexPattern = Pattern.compile("^[0-9]+$");
        final Class<?> plainClass = CharSequence.class;
        final Predicate<?> predicate = o -> false;
        final Object nothing = null;

        final Selector<?> plainObjectSelector = dynamic(plainObject);
        final Selector<?> regexSelector = dynamic(regexPattern);
        final Selector<?> classSelector = dynamic(plainClass);
        final Selector<?> predicateSelector = dynamic(predicate);
        final Selector<?> passThroughSelector = dynamic(nothing);

        assertTrue("Object selector was not correctly produced by Selectors#dynamic for plain Object type", plainObjectSelector instanceof ObjectSelector);
        assertTrue("Regex selector was not correctly produced by Selectors#dynamic for Pattern type", regexSelector instanceof RegexSelector);
        assertTrue("Class selector was not correctly produced by Selectors#dynamic for Class type", classSelector instanceof ClassSelector);
        assertTrue("Predicate selector was not correctly produced by Selectors#dynamic for Predicate type", predicateSelector instanceof PredicateSelector);
        assertTrue("Pass-through selector was not correctly produced by Selectors#dynamic for null type", passThroughSelector instanceof PassThroughSelector);
    }
}
