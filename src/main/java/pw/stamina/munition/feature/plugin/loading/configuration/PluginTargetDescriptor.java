package pw.stamina.munition.feature.plugin.loading.configuration;

import pw.stamina.munition.core.ExtensionDescriptor;
import pw.stamina.munition.core.versioning.Version;

import java.util.Objects;

/**
 * @author Mark Johnson
 */
public final class PluginTargetDescriptor {

    private final String targetName;

    private final String targetBundle;

    private final Version maxTargetVersion;

    private final Version minTargetVersion;

    public PluginTargetDescriptor(final String targetName, final String targetBundle, final Version minTargetVersion, final Version maxTargetVersion) {
        this.targetName = Objects.requireNonNull(targetName, "The target name cannot be null");
        this.targetBundle = Objects.requireNonNull(targetBundle, "The target bundle cannot be null");
        this.minTargetVersion = minTargetVersion == null ? Version.MIN_VERSION : minTargetVersion;
        this.maxTargetVersion = maxTargetVersion == null ? Version.MAX_VERSION : maxTargetVersion;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getTargetBundle() {
        return targetBundle;
    }

    public Version getMaxTargetVersion() {
        return maxTargetVersion;
    }

    public Version getMinTargetVersion() {
        return minTargetVersion;
    }

    public boolean isCompatibleWith(final ExtensionDescriptor extensionDescriptor) {
        return targetName.equals(extensionDescriptor.getLabel()) &&
                targetBundle.equals(extensionDescriptor.getBundle()) &&
                (extensionDescriptor.getVersion().compareTo(minTargetVersion) >= 0 &&
                        extensionDescriptor.getVersion().compareTo(maxTargetVersion) <= 0);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof PluginTargetDescriptor)) return false;
        final PluginTargetDescriptor that = (PluginTargetDescriptor) o;
        return Objects.equals(targetName, that.targetName) &&
                Objects.equals(targetBundle, that.targetBundle) &&
                Objects.equals(maxTargetVersion, that.maxTargetVersion) &&
                Objects.equals(minTargetVersion, that.minTargetVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetName, targetBundle, maxTargetVersion, minTargetVersion);
    }

    @Override
    public String toString() {
        return "PluginTargetDescriptor{" +
                "targetName='" + targetName + '\'' +
                ", targetBundle='" + targetBundle + '\'' +
                ", maxTargetVersion=" + maxTargetVersion +
                ", minTargetVersion=" + minTargetVersion +
                '}';
    }
}
