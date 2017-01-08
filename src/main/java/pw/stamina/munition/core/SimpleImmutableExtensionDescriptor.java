package pw.stamina.munition.core;

import pw.stamina.munition.core.metadata.Author;
import pw.stamina.munition.core.versioning.Version;

import java.util.Collections;
import java.util.List;

/**
 * @author Mark Johnson
 */
public class SimpleImmutableExtensionDescriptor extends AbstractExtensionDescriptor {

    public SimpleImmutableExtensionDescriptor(final String label, final String bundle, final Version version, final List<Author> authors) {
        super(label, bundle, version, authors);
    }

    public SimpleImmutableExtensionDescriptor(final String label, final String bundle, final Version version) {
        super(label, bundle, version);
    }

    @Override
    public List<Author> getAuthors() {
        return Collections.unmodifiableList(super.getAuthors());
    }

}
