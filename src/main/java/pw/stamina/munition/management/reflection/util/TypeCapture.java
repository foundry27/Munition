package pw.stamina.munition.management.reflection.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Mark Johnson
 */
public abstract class TypeCapture<T> {

    protected TypeCapture() {}

    protected final Type capture() {
        final Type superClass = getClass().getGenericSuperclass();
        if (!(superClass instanceof ParameterizedType)) {
            throw new IllegalStateException(String.format("%s is not parameterized and cannot have its type captured", superClass.getTypeName()));
        }
        return ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }
}