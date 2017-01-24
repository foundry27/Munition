package pw.stamina.munition.test.tests;

import org.junit.Test;
import pw.stamina.munition.facade.reflection.ClassFacade;
import pw.stamina.munition.facade.reflection.MethodFacade;

import java.lang.reflect.InvocationTargetException;

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
}
