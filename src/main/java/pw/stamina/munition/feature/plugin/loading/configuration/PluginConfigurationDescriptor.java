package pw.stamina.munition.feature.plugin.loading.configuration;

import java.util.Objects;

/**
 * @author Mark Johnson
 */
public final class PluginConfigurationDescriptor {

    private final PluginTargetDescriptor pluginTargetDescriptor;

    private final PluginIncludesDescriptor pluginIncludesDescriptor;

    public PluginConfigurationDescriptor(final PluginTargetDescriptor pluginTargetDescriptor, final PluginIncludesDescriptor pluginIncludesDescriptor) {
        this.pluginTargetDescriptor = pluginTargetDescriptor;
        this.pluginIncludesDescriptor = pluginIncludesDescriptor;
    }

    public PluginTargetDescriptor getPluginTarget() {
        return pluginTargetDescriptor;
    }

    public PluginIncludesDescriptor getPluginIncludes() {
        return pluginIncludesDescriptor;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof PluginConfigurationDescriptor)) return false;
        final PluginConfigurationDescriptor that = (PluginConfigurationDescriptor) o;
        return Objects.equals(pluginTargetDescriptor, that.pluginTargetDescriptor) &&
                Objects.equals(pluginIncludesDescriptor, that.pluginIncludesDescriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pluginTargetDescriptor, pluginIncludesDescriptor);
    }

    @Override
    public String toString() {
        return "PluginConfigurationDescriptor{" +
                "pluginTarget=" + pluginTargetDescriptor +
                ", pluginIncludes=" + pluginIncludesDescriptor +
                '}';
    }
}
