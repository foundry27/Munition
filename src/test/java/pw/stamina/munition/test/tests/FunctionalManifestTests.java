package pw.stamina.munition.test.tests;

import org.junit.Test;
import pw.stamina.munition.feature.annotated.AnnotatedFeature;
import pw.stamina.munition.feature.annotated.AnnotatedPlugin;
import pw.stamina.munition.feature.annotated.FeatureModel;
import pw.stamina.munition.feature.annotated.versioning.VersionModel;
import pw.stamina.munition.feature.core.Feature;
import pw.stamina.munition.feature.plugin.Plugin;
import pw.stamina.munition.management.manifest.Manifest;
import pw.stamina.munition.management.manifest.Manifests;

import static org.junit.Assert.assertEquals;

/**
 * @author Mark Johnson
 */
public class FunctionalManifestTests {
    @Test
    public void testUsingFunctionalManifest() {
        final Manifest<Feature> featureManifest = Manifests.newManifest();
        featureManifest.register(new PluginA());
        featureManifest.register(new FeatureB());

        assertEquals(2, featureManifest.count());


        final Manifest<Plugin> pluginManifest = featureManifest
                .filter(Plugin.class::isInstance)
                .mapDown(Plugin.class::cast);

        assertEquals(1, pluginManifest.count());

        assertEquals(PluginA.class, pluginManifest.findFirst().get().getClass());


        pluginManifest.evict();

        assertEquals(1, featureManifest.count());

        assertEquals(FeatureB.class, featureManifest.findFirst().get().getClass());


        featureManifest.register(new PluginA());

        assertEquals(1, pluginManifest.count());

        assertEquals(PluginA.class, pluginManifest.findFirst().get().getClass());

        featureManifest.evict();

        assertEquals(0, featureManifest.count());
    }

    @FeatureModel(label = "A", bundle = "a.b.c.d", version = @VersionModel(major = 1, minor = 0, patch = 0))
    private static class PluginA extends AnnotatedPlugin {

        @Override
        public void load() {

        }

        @Override
        public void unload() {

        }

        @Override
        public boolean isLoaded() {
            return false;
        }
    }

    @FeatureModel(label = "B", bundle = "a.b.c.d", version = @VersionModel(major = 1, minor = 0, patch = 0))
    private static class FeatureB extends AnnotatedFeature {

    }
}
