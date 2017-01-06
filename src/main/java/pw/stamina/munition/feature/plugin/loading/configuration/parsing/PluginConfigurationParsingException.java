package pw.stamina.munition.feature.plugin.loading.configuration.parsing;

/**
 * @author Mark Johnson
 */
public class PluginConfigurationParsingException extends RuntimeException {

    public PluginConfigurationParsingException() {
        super();
    }

    public PluginConfigurationParsingException(String message) {
        super(message);
    }

    public PluginConfigurationParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PluginConfigurationParsingException(Throwable cause) {
        super(cause);
    }

    protected PluginConfigurationParsingException(String message, Throwable cause,
                                                  boolean enableSuppression,
                                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
