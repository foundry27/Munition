package pw.stamina.munition.test.tests;

import org.junit.Test;
import pw.stamina.munition.core.ExtensionDescriptors;
import pw.stamina.munition.core.versioning.Version;
import pw.stamina.munition.core.versioning.VersionTag;
import pw.stamina.munition.feature.annotated.AnnotatedPlugin;
import pw.stamina.munition.feature.annotated.FeatureModel;
import pw.stamina.munition.feature.annotated.versioning.VersionModel;
import pw.stamina.munition.feature.plugin.Plugin;
import pw.stamina.munition.feature.plugin.loading.PluginLoader;
import pw.stamina.munition.feature.plugin.loading.PluginLoaders;
import pw.stamina.munition.feature.plugin.loading.configuration.PluginConfigurationDescriptor;
import pw.stamina.munition.feature.plugin.loading.configuration.parsing.StandardPluginConfigurationParser;
import pw.stamina.munition.feature.plugin.loading.includes.IncludeResolver;
import pw.stamina.munition.feature.plugin.loading.includes.instantiation.InstantiationStrategy;
import pw.stamina.munition.feature.plugin.loading.includes.instantiation.ZeroArgumentConstructorInstantiationStrategy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Mark Johnson
 */
public class PluginLoadingTests {
    @Test
    public void testRetrievingPluginDetailsUsingXML() throws Exception {
        final String xml = ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<plugin>" +
                    "<target>" +
                        "<id>" + "Foo" + "</id>" +
                        "<bundle>" + "a.b.c" + "</bundle>" +
                        "<minversion>" + "0.1.0-SNAPSHOT" + "</minversion>" +
                        "<maxversion>" + "0.2.0" + "</maxversion>" +
                    "</target>" +

                    "<includes>" +
                        "<include>" + "a.b.c.PluginA" + "</include>" +
                    "</includes>" +
                "</plugin>");

        final URL mockUrl = mock(URL.class);
        when(mockUrl.openStream()).then(invocationOnMock -> new ByteArrayInputStream(xml.getBytes("UTF-8")));

        final PluginConfigurationDescriptor descriptor = StandardPluginConfigurationParser.getInstance().parsePluginConfiguration(mockUrl);

        assertEquals("Foo", descriptor.getPluginTarget().getTargetName());
        assertEquals("a.b.c", descriptor.getPluginTarget().getTargetBundle());
        assertEquals(Version.of(0, 2, 0), descriptor.getPluginTarget().getMaxTargetVersion());
        assertEquals(Version.of(0, 1, 0, VersionTag.SNAPSHOT), descriptor.getPluginTarget().getMinTargetVersion());

        final List<String> includes = new ArrayList<>();
        descriptor.getPluginIncludes().getIncludeData().forEach(includes::add);
        assertEquals(Collections.singletonList("a.b.c.PluginA"), includes);
    }

    @Test
    public void testLoadingPluginUsingPluginLoader() throws IOException, ClassNotFoundException {
        final String xml = ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<plugin>" +
                    "<target>" +
                        "<id>" + "Foo" + "</id>" +
                        "<bundle>" + "a.b.c" + "</bundle>" +
                        "<minversion>" + "0.1.0-SNAPSHOT" + "</minversion>" +
                        "<maxversion>" + "0.2.0" + "</maxversion>" +
                    "</target>" +

                    "<includes>" +
                        "<include>" + "a.b.c.PluginA" + "</include>" +
                        "<include>" + "a.b.c.PluginB" + "</include>" +
                    "</includes>" +
                "</plugin>");

        final URL mockUrl = mock(URL.class);
        when(mockUrl.openStream())
                .then(invocationOnMock -> new ByteArrayInputStream(xml.getBytes("UTF-8")));

        final ClassLoader mockClassLoader = mock(ClassLoader.class);
        when(mockClassLoader.getResources(anyString()))
                .thenReturn(new Vector<>(Collections.singleton(mockUrl)).elements());

        final InstantiationStrategy<Plugin> instantiationStrategy = ZeroArgumentConstructorInstantiationStrategy.getInstance();

        final IncludeResolver<Plugin> mockIncludeResolver = mock(IncludeResolver.class);
        when(mockIncludeResolver.resolveInclude("a.b.c.PluginA"))
                .thenReturn(instantiationStrategy.instantiate(PluginA.class));
        when(mockIncludeResolver.resolveInclude("a.b.c.PluginB"))
                .thenReturn(instantiationStrategy.instantiate(PluginB.class));


        final PluginLoader loader = PluginLoaders.builder(ExtensionDescriptors.from("Foo", "a.b.c", Version.of(0, 1, 5)), mockClassLoader)
                .usingIncludeResolver(mockIncludeResolver)
                .build();

        final Iterator<Plugin> iterator = loader.iterator();
        assertEquals(iterator.next(), new PluginA());
        assertEquals(iterator.next(), new PluginB());
    }

    @FeatureModel(
            label = "PluginA",
            version = @VersionModel(major = 0, minor = 1, patch = 0),
            bundle = "a.b.c"
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
            label = "PluginB",
            version = @VersionModel(major = 0, minor = 1, patch = 0),
            bundle = "a.b.c"
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
