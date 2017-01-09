package pw.stamina.munition.feature.plugin.loading.configuration.parsing;

import pw.stamina.munition.feature.plugin.loading.configuration.PluginConfigurationDescriptor;

import java.net.URL;
import java.util.Objects;

/**
 * @author Mark Johnson
 */
public final class NonnullValidatingPluginConfigurationParser implements PluginConfigurationParser {

    private final PluginConfigurationParser backingParser;

    public NonnullValidatingPluginConfigurationParser(final PluginConfigurationParser backingParser) {
        this.backingParser = Objects.requireNonNull(backingParser, "The backing parser cannot be null");
    }

    @Override
    public PluginConfigurationDescriptor parsePluginConfiguration(final URL configurationURL) throws PluginConfigurationParsingException {
        if (configurationURL == null) {
            throw new NullPointerException("The configuration URL to be used cannot be null");
        } else {
            return backingParser.parsePluginConfiguration(configurationURL);
        }
    }
}
