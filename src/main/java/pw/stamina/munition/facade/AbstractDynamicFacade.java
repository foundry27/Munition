package pw.stamina.munition.facade;

import java.util.Set;

/**
 * @author Mark Johnson
 */
public abstract class AbstractDynamicFacade<T, F extends Facade<?>> implements DynamicFacade<T, F> {

    protected final Set<? extends FacadeTrait<? extends F>> traits;

    protected AbstractDynamicFacade(final Set<? extends FacadeTrait<? extends F>> traits) {
        this.traits = traits;
    }

    @Override
    public abstract T reify() throws ReificationException;

    @Override
    public boolean hasTrait(final FacadeTrait<? extends F> trait) {
        return traits.contains(trait);
    }
}
