package pw.stamina.munition.management.manifest;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Mark Johnson
 */
public class Manifests {
    public static <T> Manifest<T> newManifest(final Collection<T> elementCollection) {
        return new PipelineHeadManifest<>(elementCollection);
    }

    public static <T> Manifest<T> newManifest() {
        return newManifest(new ArrayList<>());
    }
}
