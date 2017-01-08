package pw.stamina.munition.feature.plugin.loading.includes;

import pw.stamina.munition.feature.plugin.loading.PluginLoadingException;

/**
 * @author Mark Johnson
 */
public class PluginIncludeResolutionException extends PluginLoadingException {

    public PluginIncludeResolutionException() {
        super();
    }

    public PluginIncludeResolutionException(String message) {
        super(message);
    }

    public PluginIncludeResolutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PluginIncludeResolutionException(Throwable cause) {
        super(cause);
    }

    protected PluginIncludeResolutionException(String message, Throwable cause,
                                               boolean enableSuppression,
                                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
