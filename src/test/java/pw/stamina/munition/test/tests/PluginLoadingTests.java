package pw.stamina.munition.test.tests;

import org.junit.Test;
import pw.stamina.munition.core.versioning.Version;
import pw.stamina.munition.core.versioning.VersionTag;
import pw.stamina.munition.feature.plugin.loading.configuration.PluginConfigurationDescriptor;
import pw.stamina.munition.feature.plugin.loading.configuration.parsing.StandardPluginConfigurationParser;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Mark Johnson
 */
public class PluginLoadingTests {
    @Test
    public void testRetrievingPluginDetailsFromJARUsingXML() throws Exception {
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
        descriptor.getPluginIncludes().getIncludedPluginClasses().forEach(includes::add);
        assertEquals(Collections.singletonList("a.b.c.PluginA"), includes);
    }

    @Test
    public void testLoadingPluginUsingPluginLoader() {}
}
