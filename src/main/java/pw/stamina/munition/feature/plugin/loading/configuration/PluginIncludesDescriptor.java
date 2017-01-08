package pw.stamina.munition.feature.plugin.loading.configuration;

import java.util.Objects;

/**
 * @author Mark Johnson
 */
public final class PluginIncludesDescriptor {

    private final Iterable<String> includeData;

    public PluginIncludesDescriptor(final Iterable<String> includeData) {
        this.includeData = includeData;
    }

    public Iterable<String> getIncludeData() {
        return includeData;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof PluginIncludesDescriptor)) return false;
        final PluginIncludesDescriptor that = (PluginIncludesDescriptor) o;
        return Objects.equals(includeData, that.includeData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(includeData);
    }

    @Override
    public String toString() {
        return "PluginIncludesDescriptor{" +
                "includes=" + includeData +
                '}';
    }
}
