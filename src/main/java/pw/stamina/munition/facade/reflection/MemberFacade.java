package pw.stamina.munition.facade.reflection;

import pw.stamina.munition.facade.Facade;

/**
 * @author Mark Johnson
 */
public interface MemberFacade<T> extends Facade<T> {
    ClassFacade getDeclaringClass();
}
