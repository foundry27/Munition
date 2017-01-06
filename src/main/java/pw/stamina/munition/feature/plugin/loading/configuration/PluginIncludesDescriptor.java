package pw.stamina.munition.feature.plugin.loading.configuration;

import java.util.Objects;

/**
 * @author Mark Johnson
 */
public final class PluginIncludesDescriptor {

    private final Iterable<String> pluginIncludes;

    public PluginIncludesDescriptor(final Iterable<String> pluginClasses) {
        this.pluginIncludes = pluginClasses;
    }

    public Iterable<String> getIncludedPluginClasses() {
        return pluginIncludes;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof PluginIncludesDescriptor)) return false;
        final PluginIncludesDescriptor that = (PluginIncludesDescriptor) o;
        return Objects.equals(pluginIncludes, that.pluginIncludes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pluginIncludes);
    }

    @Override
    public String toString() {
        return "PluginIncludesDescriptor{" +
                "includes=" + pluginIncludes +
                '}';
    }
}
