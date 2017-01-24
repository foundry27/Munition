package pw.stamina.munition.facade.locating;

/**
 * @author Mark Johnson
 */
public class LocationException extends RuntimeException {
    public LocationException() {
        super();
    }

    public LocationException(final String message) {
        super(message);
    }

    public LocationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public LocationException(final Throwable cause) {
        super(cause);
    }

    protected LocationException(final String message, final Throwable cause,
                                final boolean enableSuppression,
                                final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
