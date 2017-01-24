package pw.stamina.munition.management.manifest;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Mark Johnson
 */
public class Manifests {
    public static <T> Manifest<T> newManifest(final Collection<T> elementCollection) {
        return new PipelineHeadMutableManifest<>(elementCollection);
    }

    public static <T> MutableManifest<T> newManifest() {
        return new PipelineHeadMutableManifest<>(new ArrayList<>());
    }
}
