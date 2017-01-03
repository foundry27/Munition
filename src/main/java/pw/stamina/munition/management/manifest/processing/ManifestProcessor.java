package pw.stamina.munition.management.manifest.processing;

import pw.stamina.munition.management.manifest.Manifest;

/**
 * @author Mark Johnson
 */
public interface ManifestProcessor<IN, OUT> {
    Manifest<OUT> processManifest(Manifest<IN> inManifest);
}
