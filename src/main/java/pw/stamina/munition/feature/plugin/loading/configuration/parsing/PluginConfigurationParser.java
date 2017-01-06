package pw.stamina.munition.feature.plugin.loading.configuration.parsing;

import pw.stamina.munition.feature.plugin.loading.configuration.PluginConfigurationDescriptor;

import java.net.URL;

/**
 * @author Mark Johnson
 */
public interface PluginConfigurationParser {
    PluginConfigurationDescriptor parsePluginConfiguration(URL configurationURL) throws PluginConfigurationParsingException;
}
