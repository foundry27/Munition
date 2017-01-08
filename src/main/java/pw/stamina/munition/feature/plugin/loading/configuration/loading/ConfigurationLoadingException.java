package pw.stamina.munition.feature.plugin.loading.configuration.loading;

import pw.stamina.munition.feature.plugin.loading.PluginLoadingException;

/**
 * @author Mark Johnson
 */
public class ConfigurationLoadingException extends PluginLoadingException {

    public ConfigurationLoadingException() {
        super();
    }

    public ConfigurationLoadingException(String message) {
        super(message);
    }

    public ConfigurationLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationLoadingException(Throwable cause) {
        super(cause);
    }

    protected ConfigurationLoadingException(String message, Throwable cause,
                                            boolean enableSuppression,
                                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
