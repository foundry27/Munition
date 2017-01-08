package pw.stamina.munition.feature.plugin.loading.configuration.loading;

import java.net.URL;
import java.util.Iterator;

/**
 * @author Mark Johnson
 */
public interface PluginConfigurationLoader {
    Iterator<URL> loadConfigurations(String configurationFileName) throws ConfigurationLoadingException;
}
