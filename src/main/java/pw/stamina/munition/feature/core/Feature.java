package pw.stamina.munition.feature.core;

import pw.stamina.munition.core.traits.Bundled;
import pw.stamina.munition.core.traits.Labeled;
import pw.stamina.munition.core.versioning.Version;
import pw.stamina.munition.feature.core.metadata.FeatureMetadata;
import pw.stamina.munition.core.versioning.Versioned;

import java.util.Optional;

/**
 * @author Mark Johnson
 */
public interface Feature extends Labeled, Bundled, Versioned {
    @Override
    String getLabel();

    @Override
    String getBundle();

    @Override
    Version getVersion();

    Optional<FeatureMetadata> findMetadata();
}
