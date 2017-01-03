package pw.stamina.munition.feature.annotated;

/**
 * @author Mark Johnson
 */
public class AnnotationMissingException extends RuntimeException {

    public AnnotationMissingException() {
        super();
    }

    public AnnotationMissingException(String message) {
        super(message);
    }

    public AnnotationMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnnotationMissingException(Throwable cause) {
        super(cause);
    }

    protected AnnotationMissingException(String message, Throwable cause,
                                         boolean enableSuppression,
                                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
