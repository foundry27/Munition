package pw.stamina.munition.feature.core.dependency;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Mark Johnson
 */
public abstract class AbstractDependency<T> implements Dependency<T> {

    protected ResolutionStatus resolutionStatus;

    private final Type dependantType;

    protected AbstractDependency() {
        resolutionStatus = ResolutionStatus.NOT_ATTEMPTED;
        dependantType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public ResolutionStatus getResolutionStatus() {
        return resolutionStatus;
    }

    @Override
    public Type getDependantType() {
        return dependantType;
    }
}
