package pw.stamina.munition.management.manifest.processing;

import pw.stamina.munition.management.manifest.ImmutableManifest;

/**
 * @author Mark Johnson
 */
public interface ManifestProcessor<IN, OUT> {
    ImmutableManifest<OUT> processManifest(ImmutableManifest<IN> inManifest);
}
