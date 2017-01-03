package pw.stamina.munition.feature.simple;

import pw.stamina.munition.core.versioning.Version;
import pw.stamina.munition.feature.core.Module;
import pw.stamina.munition.feature.core.metadata.FeatureMetadata;

import java.util.Objects;

/**
 * @author Mark Johnson
 */
public abstract class SimpleModule extends SimpleFeature implements Module {


    protected SimpleModule(final String label, final String bundle, final Version version, final FeatureMetadata metadata) {
        super(label, bundle, version, metadata);
    }

    protected SimpleModule(final String label, final String bundle, final Version version) {
        this(label, bundle, version, null);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SimpleModule that = (SimpleModule) o;
        return Objects.equals(getLabel(), that.getLabel()) &&
                Objects.equals(getBundle(), that.getBundle()) &&
                Objects.equals(getVersion(), that.getVersion()) &&
                Objects.equals(findMetadata(), that.findMetadata());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "SimpleModule{" +
                "label='" + getLabel() + '\'' +
                ", bundle='" + getBundle() + '\'' +
                ", version=" + getVersion() +
                ", metadata=" + findMetadata() +
                '}';
    }
}
