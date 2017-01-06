package pw.stamina.munition.test.tests;

import org.junit.Test;
import pw.stamina.munition.core.metadata.Author;
import pw.stamina.munition.core.versioning.Version;
import pw.stamina.munition.core.versioning.VersionTag;
import pw.stamina.munition.feature.annotated.AnnotatedPlugin;
import pw.stamina.munition.feature.annotated.FeatureModel;
import pw.stamina.munition.feature.annotated.dependency.*;
import pw.stamina.munition.feature.annotated.metadata.AuthorModel;
import pw.stamina.munition.feature.annotated.metadata.MetadataModel;
import pw.stamina.munition.feature.annotated.versioning.VersionModel;
import pw.stamina.munition.feature.core.Feature;
import pw.stamina.munition.feature.plugin.Plugin;
import pw.stamina.munition.feature.plugin.dependency.Dependency;
import pw.stamina.munition.feature.plugin.dependency.resolution.DependencyResolver;
import pw.stamina.munition.feature.plugin.dependency.resolution.DependencyResolverBuilder;
import pw.stamina.munition.feature.core.metadata.FeatureMetadata;
import pw.stamina.munition.management.reflection.util.TypeToken;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Mark Johnson
 */

public class PluginAnnotationsTests {

    @Test
    public void testRetrievingFeatureModelDataFromAnnotations() {
        final Plugin pluginA = new PluginA();

        assertEquals("A", pluginA.getLabel());
        assertEquals("a.b.c.d", pluginA.getBundle());
        assertEquals(new Version(0, 1, 0, EnumSet.of(VersionTag.SNAPSHOT), Collections.singletonList("foo")), pluginA.getVersion());

        final Optional<FeatureMetadata> metadataOptional = pluginA.findMetadata();
        assertTrue("PluginA does not have a metadata annotation", metadataOptional.isPresent());

        final FeatureMetadata metadata = metadataOptional.get();
        assertTrue("PluginA metadata does not include a description", metadata.findDescription().isPresent());
        assertEquals("Feature A", metadata.findDescription().get());

        assertEquals(Collections.singletonList(new Author("Foo", "Foo Bar", "foo@bar.quz", TimeZone.getTimeZone("GMT"))), metadata.getAuthors());
    }

    @Test
    public void testRetrievingPluginDependencyDataFromAnnotations() {
        final Plugin pluginA = new PluginA();
        final Set<Dependency<?>> dependencies = pluginA.getDependencies();

        assertEquals(3, dependencies.size());

        final DependencyResolver<Object> dependencyResolver = new DependencyResolverBuilder<>()
                .withMapping(new TypeToken<Iterable<Feature>>() {}, Collections.singletonList(new PluginB()))
                .withMapping(ClassLoader.class, Thread.currentThread().getContextClassLoader())
                .build();

        for (final Dependency<?> dependency : dependencies) {
            dependency.tryResolving(dependencyResolver);
        }
    }

    @FeatureModel(
            label = "A",
            version = @VersionModel(major = 0, minor = 1, patch = 0, tags = {VersionTag.SNAPSHOT}, meta = {"foo"}),
            bundle = "a.b.c.d",
            meta = @MetadataModel(
                    authors = {
                        @AuthorModel(
                                alias = "Foo",
                                name = "Foo Bar",
                                email = "foo@bar.quz",
                                timezone = "GMT"
                        )
                    },
                    description = "Feature A"
            )
    )
    @Dependencies(
            features = {
                    @DependsOnFeature(
                            label = "B",
                            bundle = "a.b.c.d",
                            min = @VersionModel(major = 0, minor = 1, patch = 1),
                            max = @VersionModel(major = 1, minor = 0, patch = 2)
                    )
            },
            classes = {
                    @DependsOnClass(value = "java.lang.Integer", methods = {
                            @DependsOnMethod(name = "valueOf", parameters = {int.class})
                    })
            },
            packages = {
                    @DependsOnPackage("java.lang")
            }
    )
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

    @FeatureModel(
            label = "B",
            version = @VersionModel(major = 1, minor = 0, patch = 1),
            bundle = "a.b.c.d"
    )
    private static class PluginB extends AnnotatedPlugin {

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
}
