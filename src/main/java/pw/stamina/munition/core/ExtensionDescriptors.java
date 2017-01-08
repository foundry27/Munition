package pw.stamina.munition.core;

import pw.stamina.munition.core.metadata.Author;
import pw.stamina.munition.core.versioning.Version;

import java.util.Arrays;

/**
 * @author Mark Johnson
 */
public final class ExtensionDescriptors {

    private ExtensionDescriptors() {}

    public static ExtensionDescriptor from(final String label, final String bundle, final Version version, final Author... authors) {
        return new SimpleImmutableExtensionDescriptor(label, bundle, version, Arrays.asList(authors));
    }
}
