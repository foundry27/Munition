package pw.stamina.munition.feature.plugin.dependency;

/**
 * @author Mark Johnson
 */
public class DependencyResolutionException extends RuntimeException {

    public DependencyResolutionException() {
        super();
    }

    public DependencyResolutionException(String message) {
        super(message);
    }

    public DependencyResolutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DependencyResolutionException(Throwable cause) {
        super(cause);
    }

    protected DependencyResolutionException(String message, Throwable cause,
                                            boolean enableSuppression,
                                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
