package pw.stamina.munition.feature.plugin.loading.instantiation;

/**
 * @author Mark Johnson
 */
public class ObjectInstantiationException extends RuntimeException {

    public ObjectInstantiationException() {
        super();
    }

    public ObjectInstantiationException(String message) {
        super(message);
    }

    public ObjectInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectInstantiationException(Throwable cause) {
        super(cause);
    }

    protected ObjectInstantiationException(String message, Throwable cause,
                                           boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
