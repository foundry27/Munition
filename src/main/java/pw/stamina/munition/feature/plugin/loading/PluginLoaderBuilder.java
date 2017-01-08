package pw.stamina.munition.feature.plugin.loading;

import pw.stamina.munition.core.ExtensionDescriptor;
import pw.stamina.munition.feature.plugin.Plugin;
import pw.stamina.munition.feature.plugin.loading.configuration.loading.ClassLoaderResourcePluginConfigurationLoader;
import pw.stamina.munition.feature.plugin.loading.configuration.loading.PluginConfigurationLoader;
import pw.stamina.munition.feature.plugin.loading.configuration.parsing.PluginConfigurationParser;
import pw.stamina.munition.feature.plugin.loading.configuration.parsing.StandardPluginConfigurationParser;
import pw.stamina.munition.feature.plugin.loading.includes.LiteralClassNameIncludeResolver;
import pw.stamina.munition.feature.plugin.loading.includes.IncludeResolver;
import pw.stamina.munition.feature.plugin.loading.includes.instantiation.ZeroArgumentConstructorInstantiationStrategy;

/**
 * @author Mark Johnson
 */
public class PluginLoaderBuilder {

    private final ExtensionDescriptor extensionDescriptor;

    private final ClassLoader classLoader;

    private String configurationFilePath;

    private IncludeResolver<Plugin> includeResolver;

    private PluginConfigurationLoader pluginConfigurationLoader;

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

    public PluginLoaderBuilder usingIncludeResolver(final IncludeResolver<Plugin> includeResolver) {
        checkPrecondition("An include resolver has already been provided for this builder", this.includeResolver == null);
        this.includeResolver = includeResolver;
        return this;
    }

    public PluginLoaderBuilder usingConfigurationLoader(final PluginConfigurationLoader pluginConfigurationLoader) {
        checkPrecondition("A configuration loader has already been provided for this builder", this.pluginConfigurationLoader == null);
        this.pluginConfigurationLoader = pluginConfigurationLoader;
        return this;
    }


    public PluginLoaderBuilder usingConfigurationParser(final PluginConfigurationParser pluginConfigurationParser) {
        checkPrecondition("A configuration parser has already been provided for this builder", this.pluginConfigurationParser == null);
        this.pluginConfigurationParser = pluginConfigurationParser;
        return this;
    }

    public PluginLoader build() {
        return new PluginLoader(
                extensionDescriptor,
                includeResolver == null ? new LiteralClassNameIncludeResolver<>(Plugin.class, classLoader, ZeroArgumentConstructorInstantiationStrategy.getInstance()) : includeResolver,
                configurationFilePath == null ? PluginLoaders.getStandardConfigurationFilePath() : configurationFilePath,
                pluginConfigurationLoader == null ? new ClassLoaderResourcePluginConfigurationLoader(classLoader) : pluginConfigurationLoader,
                pluginConfigurationParser == null ? StandardPluginConfigurationParser.getInstance() : pluginConfigurationParser
        );
    }

    private static void checkPrecondition(final String message, final boolean condition) throws IllegalStateException {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}
