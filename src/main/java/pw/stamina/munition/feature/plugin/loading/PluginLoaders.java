package pw.stamina.munition.feature.plugin.loading;

import pw.stamina.munition.core.ExtensionDescriptor;

/**
 * @author Mark Johnson
 */
public final class PluginLoaders {

    private static final String STANDARD_CONFIGURATION_FILE = "META-INF/munition.xml";

    private PluginLoaders() {}

    public static PluginLoaderBuilder builder(final ExtensionDescriptor extensionDescriptor, final ClassLoader classLoader) {
        return new PluginLoaderBuilder(extensionDescriptor, classLoader);
    }

    public static String getStandardConfigurationFilePath() {
        return STANDARD_CONFIGURATION_FILE;
    }
}
