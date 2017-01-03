package pw.stamina.munition.management.reflection.util;

import java.lang.reflect.Type;

/**
 * @author Mark Johnson
 */
public abstract class TypeToken<T> extends TypeCapture<T> implements Type {

    private final Type thisType;

    protected TypeToken() {
        this.thisType = super.capture();
    }

    @Override
    public String getTypeName() {
        return thisType.getTypeName();
    }

    @Override
    public boolean equals(final Object o) {
        return o.equals(thisType);
    }

    @Override
    public int hashCode() {
        return thisType.hashCode();
    }

    @Override
    public String toString() {
        return thisType.toString();
    }
}
