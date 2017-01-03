package pw.stamina.munition.management.manifest.processing;

import pw.stamina.munition.management.manifest.Manifest;

/**
 * @author Mark Johnson
 */
public interface UnaryManifestProcessor<T> extends ManifestProcessor<T, T> {
    @Override
    Manifest<T> processManifest(Manifest<T> initialManifest);
}
