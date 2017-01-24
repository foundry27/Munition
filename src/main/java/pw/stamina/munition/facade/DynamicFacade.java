package pw.stamina.munition.facade;

/**
 * @author Mark Johnson
 */
public interface DynamicFacade<T, F extends Facade<?>> extends Facade<T> {
    boolean hasTrait(FacadeTrait<? extends F> trait);
}
