package pw.stamina.munition.facade.locating;

import java.lang.reflect.Member;

/**
 * @author Mark Johnson
 */
public interface MemberLocator<M extends Member> extends Locator<M, Class<?>> {
    @Override
    M locate(Class<?> targetClass);
}
