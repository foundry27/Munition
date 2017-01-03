package pw.stamina.munition.feature.annotated;

import pw.stamina.munition.core.versioning.Version;
import pw.stamina.munition.feature.core.Module;
import pw.stamina.munition.feature.core.metadata.FeatureMetadata;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Mark Johnson
 */
public abstract class AnnotatedModule extends AnnotatedFeature implements Module {

    protected AnnotatedModule() {
        super();
    }

    @Override
    public String getLabel() {
        return super.getLabel();
    }

    @Override
    public Version getVersion() {
        return super.getVersion();
    }

    @Override
    public String getBundle() {
        return super.getBundle();
    }

    @Override
    public Optional<FeatureMetadata> findMetadata() {
        return super.findMetadata();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AnnotatedModule that = (AnnotatedModule) o;
        return Objects.equals(getLabel(), that.getLabel()) &&
                Objects.equals(getVersion(), that.getVersion()) &&
                Objects.equals(getBundle(), that.getBundle()) &&
                Objects.equals(findMetadata(), that.findMetadata());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLabel(), getVersion(), getBundle(), findMetadata());
    }

    @Override
    public String toString() {
        return "AnnotatedModule{" +
                "label='" + getLabel() + '\'' +
                ", version=" + getVersion() +
                ", bundle='" + getBundle() + '\'' +
                ", metadata=" + findMetadata().map(Object::toString).orElse("none") +
                ", enabled=" + isEnabled() +
                '}';
    }
}
