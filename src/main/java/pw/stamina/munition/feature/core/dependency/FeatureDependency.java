package pw.stamina.munition.feature.core.dependency;

import pw.stamina.munition.core.versioning.Version;
import pw.stamina.munition.core.versioning.Versioned;
import pw.stamina.munition.feature.core.Feature;
import pw.stamina.munition.feature.core.Plugin;
import pw.stamina.munition.feature.core.dependency.resolution.DependencyResolver;
import pw.stamina.munition.management.reflection.util.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * @author Mark Johnson
 */
public class FeatureDependency extends AbstractDependency<Object> {

    private static final Type FEATURE_ITERABLE_TYPE;

    private final String label;

    private final String bundle;

    private final Version minVersion;

    private final Version maxVersion;

    public FeatureDependency(final String label, final String bundle, final Version minVersion, final Version maxVersion) {
        this.label = Objects.requireNonNull(label, "Feature label for dependency cannot be null");
        this.bundle = Objects.requireNonNull(bundle, "Feature bundle for dependency cannot be null");
        this.minVersion = Objects.requireNonNull(minVersion, "Minimum feature version for dependency cannot be null");
        this.maxVersion = Objects.requireNonNull(maxVersion, "Maximum feature version for dependency cannot be null");
    }

    @Override
    public void tryResolving(final DependencyResolver<? super Object> dependencyResolver) throws DependencyResolutionException {
        final Iterable<Feature> lookup = dependencyResolver.getResolutionObject(FEATURE_ITERABLE_TYPE);

        final List<Feature> canonicalFeatureMatches = StreamSupport.stream(lookup.spliterator(), false)
                .filter(this::canonicallySatisfiesThisDependency)
                .collect(Collectors.toList());

        if (canonicalFeatureMatches.isEmpty()) {
            this.resolutionStatus = ResolutionStatus.FAILED;
            throw new DependencyResolutionException(String.format("No feature(s) matching the identifier \"%s.%s\" were found.", bundle, label));
        } else {
            determineAndCacheDependencySatisfactionStatus(canonicalFeatureMatches, dependencyResolver);
        }
    }

    private boolean canonicallySatisfiesThisDependency(final Feature feature) {
        return label.equals(feature.getLabel()) && bundle.equals(feature.getBundle());
    }

    private boolean versionSatisfiesThisDependency(final Version featureVersion) {
        return featureVersion.compareTo(minVersion) >= 0 && featureVersion.compareTo(maxVersion) <= 0;
    }

    private void determineAndCacheDependencySatisfactionStatus(final Collection<Feature> canonicalFeatureMatches, final DependencyResolver<Object> dependencyResolver) {
        this.resolutionStatus = ResolutionStatus.FAILED;

        boolean wasCanonicallyMatchingFeatureFound = false;
        for (final Feature feature : canonicalFeatureMatches) {
            if (versionSatisfiesThisDependency(feature.getVersion())) {
                tryResolvingFeatureSubDependenciesIfNecessary(feature, dependencyResolver);
                this.resolutionStatus = ResolutionStatus.RESOLVED;
                break;
            } else {
                wasCanonicallyMatchingFeatureFound = true;
            }
        }

        if (this.resolutionStatus == ResolutionStatus.FAILED && wasCanonicallyMatchingFeatureFound) {
            throw new DependencyResolutionException(String.format("Feature \"%s.%s\" is present, but not within version constraints %s <= {version} <= %s. Version(s) present: %s",
                            bundle, label, minVersion, maxVersion, collectVersionStringsFromFeatures(canonicalFeatureMatches)));
        }
    }

    private static void tryResolvingFeatureSubDependenciesIfNecessary(final Feature feature, final DependencyResolver<Object> dependencyResolver) {
        if (feature instanceof Plugin) {
            for (final Dependency<?> dependency : ((Plugin) feature).getDependencies()) {
                if (dependency.getResolutionStatus() == ResolutionStatus.NOT_ATTEMPTED) {
                    try {
                        dependency.tryResolving(dependencyResolver);
                    } catch (final DependencyResolutionException e) {
                        throw new DependencyResolutionException("Exception encountered while resolving feature sub-dependencies", e);
                    }
                }
            }
        }
    }

    private static String collectVersionStringsFromFeatures(final Collection<Feature> canonicalFeatureMatches) {
        return String.join(", ", canonicalFeatureMatches.stream()
                .map(Versioned::getVersion)
                .map(Object::toString)
                .collect(Collectors.toList()));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FeatureDependency that = (FeatureDependency) o;
        return Objects.equals(label, that.label) &&
                Objects.equals(bundle, that.bundle) &&
                Objects.equals(minVersion, that.minVersion) &&
                Objects.equals(maxVersion, that.maxVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, bundle, minVersion, maxVersion);
    }

    @Override
    public String toString() {
        return "FeatureDependency{" +
                "label='" + label + '\'' +
                ", bundle='" + bundle + '\'' +
                ", minVersion=" + minVersion +
                ", maxVersion=" + maxVersion +
                '}';
    }

    static {
        FEATURE_ITERABLE_TYPE = new TypeToken<Iterable<Feature>>() {};
    }
}
