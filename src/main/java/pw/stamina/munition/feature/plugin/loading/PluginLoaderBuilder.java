package pw.stamina.munition.feature.plugin.loading;

import pw.stamina.munition.core.ExtensionDescriptor;
import pw.stamina.munition.feature.plugin.Plugin;
import pw.stamina.munition.feature.plugin.loading.configuration.loading.ClassLoaderResourcePluginConfigurationLoader;
import pw.stamina.munition.feature.plugin.loading.configuration.loading.NonnullValidatingPluginConfigurationLoader;
import pw.stamina.munition.feature.plugin.loading.configuration.loading.PluginConfigurationLoader;
import pw.stamina.munition.feature.plugin.loading.configuration.parsing.NonnullValidatingPluginConfigurationParser;
import pw.stamina.munition.feature.plugin.loading.configuration.parsing.PluginConfigurationParser;
import pw.stamina.munition.feature.plugin.loading.configuration.parsing.StandardPluginConfigurationParser;
import pw.stamina.munition.feature.plugin.loading.includes.LiteralClassNameIncludeResolver;
import pw.stamina.munition.feature.plugin.loading.includes.IncludeResolver;
import pw.stamina.munition.feature.plugin.loading.includes.instantiation.NonnullValidatingInstantiationStrategyDecorator;
import pw.stamina.munition.feature.plugin.loading.includes.instantiation.ZeroArgumentConstructorInstantiationStrategy;

import java.util.Objects;

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
        this.extensionDescriptor = Objects.requireNonNull(extensionDescriptor, "The extension descriptor cannot be null");
        this.classLoader = Objects.requireNonNull(classLoader, "The classloader cannot be null");
    }

    public PluginLoaderBuilder usingConfigurationFile(final String configurationFilePath) {
        checkPrecondition("A configuration file has already been provided for this builder", this.configurationFilePath == null);
        this.configurationFilePath = Objects.requireNonNull(configurationFilePath, "The configuration file path cannot be null");
        return this;
    }

    public PluginLoaderBuilder usingIncludeResolver(final IncludeResolver<Plugin> includeResolver) {
        checkPrecondition("An include resolver has already been provided for this builder", this.includeResolver == null);
        this.includeResolver = Objects.requireNonNull(includeResolver, "The include resolver cannot be null");
        return this;
    }

    public PluginLoaderBuilder usingConfigurationLoader(final PluginConfigurationLoader pluginConfigurationLoader) {
        checkPrecondition("A configuration loader has already been provided for this builder", this.pluginConfigurationLoader == null);
        this.pluginConfigurationLoader = Objects.requireNonNull(pluginConfigurationLoader, "The plugin configuration loader cannot be null");
        return this;
    }


    public PluginLoaderBuilder usingConfigurationParser(final PluginConfigurationParser pluginConfigurationParser) {
        checkPrecondition("A configuration parser has already been provided for this builder", this.pluginConfigurationParser == null);
        this.pluginConfigurationParser = Objects.requireNonNull(pluginConfigurationParser, "The plugin configuration parser cannot be null");
        return this;
    }

    public PluginLoader build() {
        if (includeResolver == null) {
            includeResolver = new LiteralClassNameIncludeResolver<>(Plugin.class, classLoader, new NonnullValidatingInstantiationStrategyDecorator<>(ZeroArgumentConstructorInstantiationStrategy.getInstance()));
        }
        if (configurationFilePath == null) {
            configurationFilePath = PluginLoaders.getStandardConfigurationFilePath();
        }
        if (pluginConfigurationLoader == null) {
            pluginConfigurationLoader = new NonnullValidatingPluginConfigurationLoader(new ClassLoaderResourcePluginConfigurationLoader(classLoader));
        }
        if (pluginConfigurationParser == null) {
            pluginConfigurationParser = new NonnullValidatingPluginConfigurationParser(StandardPluginConfigurationParser.getInstance());
        }
        return new PluginLoader(extensionDescriptor, includeResolver, configurationFilePath, pluginConfigurationLoader, pluginConfigurationParser);
    }

    private static void checkPrecondition(final String message, final boolean condition) throws IllegalStateException {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}
