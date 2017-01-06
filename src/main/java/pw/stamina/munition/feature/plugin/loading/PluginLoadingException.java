package pw.stamina.munition.feature.plugin.loading;

/**
 * @author Mark Johnson
 */
public class PluginLoadingException extends RuntimeException {

    public PluginLoadingException() {
        super();
    }

    public PluginLoadingException(String message) {
        super(message);
    }

    public PluginLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PluginLoadingException(Throwable cause) {
        super(cause);
    }

    protected PluginLoadingException(String message, Throwable cause,
                                     boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
