package pw.stamina.munition.feature.annotated;

import pw.stamina.munition.core.metadata.Author;
import pw.stamina.munition.core.versioning.Version;
import pw.stamina.munition.feature.annotated.metadata.MetadataModel;
import pw.stamina.munition.feature.annotated.versioning.VersionModel;
import pw.stamina.munition.feature.core.Feature;
import pw.stamina.munition.feature.core.metadata.FeatureMetadata;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * @author Mark Johnson
 */
public abstract class AnnotatedFeature implements Feature {

    private final String label;

    private final Version version;

    private final String bundle;

    private final FeatureMetadata metadata;

    protected AnnotatedFeature() {
        final FeatureModel model = getClass().getDeclaredAnnotation(FeatureModel.class);
        if (model == null) {
            throw new AnnotationMissingException(String.format("No %s annotation found on class %s", FeatureModel.class.getSimpleName(), getClass().getSimpleName()));
        }
        this.label = model.label();
        this.bundle = model.bundle();

        final VersionModel versionModel = model.version();
        this.version = Version.of(versionModel.major(), versionModel.minor(), versionModel.patch(), versionModel.tags());

        final MetadataModel metadataModel = model.meta();
        if (metadataModel.authors().length == 0 && metadataModel.description().isEmpty()) {
            this.metadata = null;
        } else {
            this.metadata = new FeatureMetadata(
                    Arrays.stream(model.meta().authors())
                            .map(author -> new Author(author.alias(), author.name(), author.email(), TimeZone.getTimeZone(author.timezone())))
                            .collect(Collectors.toList()),
                    model.meta().description());
        }
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public String getBundle() {
        return bundle;
    }

    @Override
    public Optional<FeatureMetadata> findMetadata() {
        return Optional.ofNullable(metadata);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AnnotatedFeature that = (AnnotatedFeature) o;
        return Objects.equals(label, that.label) &&
                Objects.equals(version, that.version) &&
                Objects.equals(bundle, that.bundle) &&
                Objects.equals(metadata, that.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, version, bundle, metadata);
    }

    @Override
    public String toString() {
        return "AnnotatedFeature{" +
                "label='" + label + '\'' +
                ", version=" + version +
                ", bundle='" + bundle + '\'' +
                ", metadata=" + (metadata != null ? metadata : "none") +
                '}';
    }
}
