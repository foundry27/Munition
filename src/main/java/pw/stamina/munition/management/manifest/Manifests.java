package pw.stamina.munition.management.manifest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Mark Johnson
 */
public final class Manifests {
    public static <T> MutableManifest<T> newManifest(final Collection<T> elementCollection) {
        return new PipelineHeadMutableManifest<>(elementCollection);
    }

    public static <T> MutableManifest<T> newManifest() {
        return new PipelineHeadMutableManifest<>(new ArrayList<>());
    }

    @SafeVarargs
    public static <T> ImmutableManifest<T> of(final T... elements) {
        return new PipelineHeadMutableManifest<>(Arrays.asList(elements)).toImmutable();
    }
}
