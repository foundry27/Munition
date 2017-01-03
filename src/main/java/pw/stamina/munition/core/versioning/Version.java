package pw.stamina.munition.core.versioning;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mark Johnson
 */
public final class Version implements Comparable<Version> {

    public static final Version MIN_VERSION = new Version(0, 0, 0);

    public static final Version MAX_VERSION = new Version(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

    private final int majorVersion;

    private final int minorVersion;

    private final int patchVersion;

    private final Set<VersionTag> tags;

    public Version(final int majorVersion, final int minorVersion, final int patchVersion, final Set<VersionTag> tags) {
        validateConstructorArguments(majorVersion, minorVersion, patchVersion, tags);
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.patchVersion = patchVersion;
        this.tags = tags;
    }

    public Version(final int majorVersion, final int minorVersion, final int patchVersion) {
        this(majorVersion, minorVersion, patchVersion, Collections.emptySet());
    }

    public static Version of(final int majorVersion, final int minorVersion, final int patchVersion, final VersionTag... tags) {
        final Set<VersionTag> tagSet = tags.length > 0 ? EnumSet.copyOf(Arrays.asList(tags)) : Collections.emptySet();
        return new Version(majorVersion, minorVersion, patchVersion, tagSet);
    }

    private static void validateConstructorArguments(final int majorVersion, final int minorVersion, final int patchVersion, final Set<VersionTag> tags) {
        if (majorVersion < 0) {
            throw new IllegalArgumentException("The major version number associated with this version cannot be less than zero");
        } else if (minorVersion < 0) {
            throw new IllegalArgumentException("The minor version number associated with this version cannot be less than zero");
        } else if (patchVersion < 0) {
            throw new IllegalArgumentException("The patch version number associated with this version cannot be less than zero");
        } else if (tags == null) {
            throw new IllegalArgumentException("The set of version tags associated with this version cannot be null");
        } else if (tags.contains(null)) {
            throw new IllegalArgumentException("The set of version tags associated with this version cannot contain null");
        }
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public int getPatchVersion() {
        return patchVersion;
    }

    public Set<VersionTag> getTags() {
        return tags;
    }

    @Override
    public int compareTo(final Version o) {
        return Comparator.comparingInt(Version::getMajorVersion)
                .thenComparingInt(Version::getMinorVersion)
                .thenComparingInt(Version::getPatchVersion)
                .compare(this, o);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Version version = (Version) o;
        return majorVersion == version.majorVersion &&
                minorVersion == version.minorVersion &&
                patchVersion == version.patchVersion &&
                tags.equals(version.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(majorVersion, minorVersion, patchVersion, tags);
    }

    @Override
    public String toString() {
        return String.format("%d.%d.%d%s",
                majorVersion, minorVersion, patchVersion,
                tags.isEmpty() ? "" : "-" + String.join(", ", tags.stream()
                        .map(Object::toString)
                        .collect(Collectors.toList())));
    }
}
