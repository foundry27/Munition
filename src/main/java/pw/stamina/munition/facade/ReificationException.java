package pw.stamina.munition.facade;

/**
 * @author Mark Johnson
 */
public class ReificationException extends RuntimeException {
    public ReificationException() {
        super();
    }

    public ReificationException(final String message) {
        super(message);
    }

    public ReificationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ReificationException(final Throwable cause) {
        super(cause);
    }

    protected ReificationException(final String message, final Throwable cause,
                                   final boolean enableSuppression,
                                   final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
