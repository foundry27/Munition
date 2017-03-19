package pw.stamina.munition.core.versioning;

/**
 * @author Mark Johnson
 */
public enum VersionTag implements Comparable<VersionTag> {
    SNAPSHOT("SNAPSHOT"),
    ALPHA("ALPHA"), BETA("BETA"), RELEASE_CANDIDATE("RC"), RELEASE("RELEASE");

    private final String tagName;

    VersionTag(final String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    @Override
    public String toString() {
        return getTagName();
    }
}
