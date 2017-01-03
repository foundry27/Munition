package pw.stamina.munition.core;

import pw.stamina.munition.core.metadata.Author;
import pw.stamina.munition.core.versioning.Version;

import java.util.List;

/**
 * @author Mark Johnson
 */
public abstract class AbstractClientDescriptor implements ClientDescriptor {

    private final String label;

    private final Version version;

    private final List<Author> authors;

    protected AbstractClientDescriptor(final String label, final Version version, final List<Author> authors) {
        this.label = label;
        this.version = version;
        this.authors = authors;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public List<Author> getAuthors() {
        return authors;
    }

}
