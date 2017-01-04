package pw.stamina.munition.core.versioning;

/**
 * @author Mark Johnson
 */
public enum VersionTag {
    SNAPSHOT("Snapshot");

    private final String tagName;

    VersionTag(final String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }
}
