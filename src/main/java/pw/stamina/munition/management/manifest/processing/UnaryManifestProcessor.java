package pw.stamina.munition.management.manifest.processing;

import pw.stamina.munition.management.manifest.ImmutableManifest;

/**
 * @author Mark Johnson
 */
public interface UnaryManifestProcessor<T> extends ManifestProcessor<T, T> {
    @Override
    ImmutableManifest<T> processManifest(ImmutableManifest<T> initialManifest);
}
