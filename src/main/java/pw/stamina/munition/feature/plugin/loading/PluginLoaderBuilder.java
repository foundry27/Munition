package pw.stamina.munition.feature.plugin.loading;

import pw.stamina.munition.core.ExtensionDescriptor;
import pw.stamina.munition.feature.plugin.Plugin;
import pw.stamina.munition.feature.plugin.loading.configuration.parsing.PluginConfigurationParser;
import pw.stamina.munition.feature.plugin.loading.configuration.parsing.StandardPluginConfigurationParser;
import pw.stamina.munition.feature.plugin.loading.instantiation.InstantiationStrategy;
import pw.stamina.munition.feature.plugin.loading.instantiation.ZeroArgumentConstructorInstantiationStrategy;

/**
 * @author Mark Johnson
 */
public class PluginLoaderBuilder {

    private final ExtensionDescriptor extensionDescriptor;

    private final ClassLoader classLoader;

    private String configurationFilePath;

    private InstantiationStrategy<Plugin> instantiationStrategy;

    private PluginConfigurationParser pluginConfigurationParser;

    public PluginLoaderBuilder(final ExtensionDescriptor extensionDescriptor, final ClassLoader classLoader) {
        this.extensionDescriptor = extensionDescriptor;
        this.classLoader = classLoader;
    }

    public PluginLoaderBuilder usingConfigurationFile(final String configurationFilePath) {
        checkPrecondition("A configuration file has already been provided for this builder", this.configurationFilePath == null);
        this.configurationFilePath = configurationFilePath;
        return this;
    }

    public PluginLoaderBuilder usingInstantiationStrategy(final InstantiationStrategy<Plugin> instantiationStrategy) {
        checkPrecondition("An instantiation strategy has already been provided for this builder", this.instantiationStrategy == null);
        this.instantiationStrategy = instantiationStrategy;
        return this;
    }

    public PluginLoaderBuilder usingConfigurationParser(final PluginConfigurationParser pluginConfigurationParser) {
        checkPrecondition("A configuration parser has already been provided for this builder", this.pluginConfigurationParser == null);
        this.pluginConfigurationParser = pluginConfigurationParser;
        return this;
    }

    public PluginLoader build() {
        return new PluginLoader(extensionDescriptor, classLoader,
                instantiationStrategy == null ? ZeroArgumentConstructorInstantiationStrategy.getInstance() : instantiationStrategy,
                configurationFilePath == null ? PluginLoaders.getStandardConfigurationFilePath() : configurationFilePath,
                pluginConfigurationParser == null ? StandardPluginConfigurationParser.getInstance() : pluginConfigurationParser);
    }

    private static void checkPrecondition(final String message, final boolean condition) throws IllegalStateException {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}
