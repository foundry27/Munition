package pw.stamina.munition.core;

import pw.stamina.munition.core.metadata.Author;
import pw.stamina.munition.core.versioning.Version;

import java.util.Collections;
import java.util.List;

/**
 * @author Mark Johnson
 */
public abstract class AbstractExtensionDescriptor implements ExtensionDescriptor {

    private final String label;

    private final String bundle;

    private final Version version;

    private final List<Author> authors;

    protected AbstractExtensionDescriptor(final String label, final String bundle, final Version version, final List<Author> authors) {
        this.label = label;
        this.bundle = bundle;
        this.version = version;
        this.authors = authors;
    }

    protected AbstractExtensionDescriptor(final String label, final String bundle, final Version version) {
        this(label, bundle, version, Collections.emptyList());
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getBundle() {
        return bundle;
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
