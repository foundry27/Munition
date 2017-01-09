package pw.stamina.munition.feature.plugin.loading.configuration.parsing;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pw.stamina.munition.core.versioning.Version;
import pw.stamina.munition.feature.plugin.loading.configuration.PluginConfigurationDescriptor;
import pw.stamina.munition.feature.plugin.loading.configuration.PluginIncludesDescriptor;
import pw.stamina.munition.feature.plugin.loading.configuration.PluginTargetDescriptor;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Mark Johnson
 */
public final class StandardPluginConfigurationParser implements PluginConfigurationParser {

    private static final StandardPluginConfigurationParser INSTANCE = new StandardPluginConfigurationParser();

    private static final URL XML_SCHEMA_URL;

    private StandardPluginConfigurationParser() {}

    @Override
    public PluginConfigurationDescriptor parsePluginConfiguration(final URL configurationURL) throws PluginConfigurationParsingException {
        try {
            tryValidatingXMLFileAgainstSchema(configurationURL.openStream(), XML_SCHEMA_URL.openStream());
            final Element rootPluginElement = getRootElementForXML(configurationURL.openStream());

            final PluginTargetDescriptor pluginTarget = extractPluginTargetFromRoot(rootPluginElement);
            final PluginIncludesDescriptor pluginIncludes = extractPluginIncludesFromRoot(rootPluginElement);
            return new PluginConfigurationDescriptor(pluginTarget, pluginIncludes);

        } catch (final IOException e) {
            throw new PluginConfigurationParsingException("Exception encountered while reading from file stream:", e);
        }
    }


    private static void tryValidatingXMLFileAgainstSchema(final InputStream xml, final InputStream xsd) {
        try {
            final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            final Schema schema = factory.newSchema(new StreamSource(xsd));
            final Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
        } catch (final SAXException e) {
            throw new PluginConfigurationParsingException("XML configuration file is not compliant with XML schema:", e);
        } catch (final IOException e) {
            throw new PluginConfigurationParsingException("Exception encountered while reading data from stream:", e);
        }
    }

    private static Element getRootElementForXML(final InputStream xml) {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.parse(xml);
            return document.getDocumentElement();
        } catch (final ParserConfigurationException e) {
            throw new PluginConfigurationParsingException("Exception encountered while configuring configuration parser:", e);
        } catch (final IOException e) {
            throw new PluginConfigurationParsingException("Exception encountered while reading configuration file from stream:", e);
        } catch (final SAXException e) {
            throw new PluginConfigurationParsingException("Exception encountered while parsing configuration file:", e);
        }
    }

    private static PluginTargetDescriptor extractPluginTargetFromRoot(final Element element) {
        final Element targetElement = (Element) element.getElementsByTagName("target").item(0);

        final String targetName = targetElement.getElementsByTagName("id").item(0).getTextContent();
        final String targetBundle = targetElement.getElementsByTagName("bundle").item(0).getTextContent();

        final NodeList minVersionLookup = targetElement.getElementsByTagName("minversion");
        final String minTargetVersionText = minVersionLookup.getLength() > 0 ? minVersionLookup.item(0).getTextContent() : null;

        final NodeList maxVersionLookup = targetElement.getElementsByTagName("maxversion");
        final String maxTargetVersionText = maxVersionLookup.getLength() > 0 ? maxVersionLookup.item(0).getTextContent() : null;

        try {
            return new PluginTargetDescriptor(
                    targetName, targetBundle,
                    minTargetVersionText != null ? SemanticVersionParser.parseVersion(minTargetVersionText) : Version.MIN_VERSION,
                    maxTargetVersionText != null ? SemanticVersionParser.parseVersion(maxTargetVersionText) : Version.MAX_VERSION
            );
        } catch (final ParseException e) {
            throw new PluginConfigurationParsingException("Exception encountered while parsing version strings from configuration:", e);
        }
    }

    private static PluginIncludesDescriptor extractPluginIncludesFromRoot(final Element element) {
        final Node includesNode = element.getElementsByTagName("includes").item(0);
        final NodeList includesNodeChildren = includesNode.getChildNodes();

        final Collection<String> includes = new ArrayList<>();
        for (int i = 0; i < includesNodeChildren.getLength(); i++) {
            final Node include = includesNodeChildren.item(i);
            includes.add(include.getTextContent());
        }
        return new PluginIncludesDescriptor(includes);
    }

    public static StandardPluginConfigurationParser getInstance() {
        return INSTANCE;
    }

    static {
        try {
            XML_SCHEMA_URL = new URL("https://gist.githubusercontent.com/foundry27/a00ea4317948bb5ff755dfae6490cfeb/raw/3e2205649cf7d3ae78a3791fec9e4738b604e51b/mandateplugin-1.0.0.xsd");
        } catch (final MalformedURLException e) {
            throw new RuntimeException("Malformed XML schema URL found:", e);
        }
    }
}
