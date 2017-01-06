package pw.stamina.munition.core;

import pw.stamina.munition.core.metadata.Author;
import pw.stamina.munition.core.metadata.Authored;
import pw.stamina.munition.core.traits.Bundled;
import pw.stamina.munition.core.traits.Labeled;
import pw.stamina.munition.core.versioning.Version;
import pw.stamina.munition.core.versioning.Versioned;

import java.util.List;

/**
 * @author Mark Johnson
 */
public interface ExtensionDescriptor extends Labeled, Bundled, Authored, Versioned {
    @Override
    String getLabel();

    @Override
    String getBundle();

    @Override
    List<Author> getAuthors();

    @Override
    Version getVersion();
}
