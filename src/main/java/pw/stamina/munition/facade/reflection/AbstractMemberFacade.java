package pw.stamina.munition.facade.reflection;

import pw.stamina.munition.facade.OptionalFacade;
import pw.stamina.munition.facade.ReificationException;
import pw.stamina.munition.facade.reflection.locating.MemberLocator;

import java.lang.reflect.Member;

/**
 * @author Mark Johnson
 */
public abstract class AbstractMemberFacade<M extends Member, L extends MemberLocator<M>> implements MemberFacade<M>, OptionalFacade<M> {

    protected final ClassFacade classFacade;

    protected final L memberLocator;

    protected boolean presenceChecked;

    protected M backingMember;

    protected AbstractMemberFacade(final ClassFacade classFacade, final L memberLocator) {
        this.classFacade = classFacade;
        this.memberLocator = memberLocator;
    }

    @Override
    public M reify() throws ReificationException {
        if (presenceChecked) {
            return backingMember;
        } else {
            presenceChecked = true;
            if (classFacade.isPresent()) {
                backingMember = memberLocator.locate(classFacade.reify());
            } else {
                throw new ReificationException(String.format("Class %s does not exist", classFacade.getName()));
            }
            return backingMember;
        }
    }

    @Override
    public boolean isPresent() {
        if (presenceChecked) {
            return backingMember != null;
        } else {
            try {
                reify();
                return true;
            } catch (final ReificationException e) {
                return false;
            }
        }
    }

    @Override
    public ClassFacade getDeclaringClass() {
        return classFacade;
    }
}
