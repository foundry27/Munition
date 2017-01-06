package pw.stamina.munition.feature.plugin.dependency.resolution;

/**
 * @author Mark Johnson
 */
public class ResolutionObjectNotFoundException extends RuntimeException {

    public ResolutionObjectNotFoundException() {
        super();
    }

    public ResolutionObjectNotFoundException(String message) {
        super(message);
    }

    public ResolutionObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResolutionObjectNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ResolutionObjectNotFoundException(String message, Throwable cause,
                                                boolean enableSuppression,
                                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
