package pw.stamina.munition.event.core;

/**
 * @author Mark Johnson
 */
public class ScanFailedException extends RuntimeException {

    public ScanFailedException() {
        super();
    }

    public ScanFailedException(String message) {
        super(message);
    }

    public ScanFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScanFailedException(Throwable cause) {
        super(cause);
    }

    protected ScanFailedException(String message, Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
