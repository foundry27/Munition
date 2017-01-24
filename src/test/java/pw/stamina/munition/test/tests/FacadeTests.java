package pw.stamina.munition.test.tests;

import org.junit.Test;
import pw.stamina.munition.facade.AbstractDynamicFacade;
import pw.stamina.munition.facade.FacadeTrait;
import pw.stamina.munition.facade.ReificationException;
import pw.stamina.munition.facade.reflection.ClassFacade;
import pw.stamina.munition.facade.reflection.MethodFacade;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Mark Johnson
 */
public class FacadeTests {
    @Test
    public void testRetrievingAndInvokingMethodFromClassFacade() {
        final ClassFacade classFacade = ClassFacade.of("java.lang.String");
        assertTrue(classFacade.isPresent());
        final MethodFacade methodFacade = classFacade.getMethod("equalsIgnoreCase", String.class);
        assertTrue(methodFacade.isPresent());
        try {
            assertTrue((boolean) methodFacade.invoke("foo", "FoO"));
        } catch (final InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUsingFacadeTraits() {
        final RealClassFacade<?> facade = new RealClassFacade<>(new RealClass());
        assertTrue(facade.hasTrait(RealClassTraits.HAS_FOO));
        assertTrue(facade.hasTrait(RealClassTraits.HAS_QUZ));
        assertFalse(facade.hasTrait(RealClassTraits.HAS_BAR));
    }

    private static class RealClass {}

    private static class RealClassFacade<T> extends AbstractDynamicFacade<T, RealClassFacade<?>> {

        private final T instance;

        RealClassFacade(final T instance) {
            super(EnumSet.of(RealClassTraits.HAS_FOO, RealClassTraits.HAS_QUZ));
            this.instance = instance;
        }

        @Override
        public T reify() throws ReificationException {
            return instance;
        }
    }

    private enum RealClassTraits implements FacadeTrait<RealClassFacade<?>> {
        HAS_FOO,
        HAS_BAR,
        HAS_QUZ
    }
}
