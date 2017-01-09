package pw.stamina.munition.feature.plugin.loading.configuration.loading;

import java.net.URL;
import java.util.Iterator;

/**
 * @author Mark Johnson
 */
public final class NonnullValidatingPluginConfigurationLoader implements PluginConfigurationLoader {

    private final PluginConfigurationLoader backingLoader;

    public NonnullValidatingPluginConfigurationLoader(final PluginConfigurationLoader backingLoader) {
        this.backingLoader = backingLoader;
    }

    @Override
    public Iterator<URL> loadConfigurations(final String configurationFileName) throws ConfigurationLoadingException {
        if (configurationFileName == null) {
            throw new NullPointerException("The configuration file name to be used cannot be null");
        } else {
            return backingLoader.loadConfigurations(configurationFileName);
        }
    }
}
