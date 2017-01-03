package pw.stamina.munition.feature.annotated;

import pw.stamina.munition.core.versioning.Version;
import pw.stamina.munition.feature.annotated.dependency.Dependencies;
import pw.stamina.munition.feature.annotated.dependency.DependsOnClass;
import pw.stamina.munition.feature.annotated.dependency.DependsOnFeature;
import pw.stamina.munition.feature.annotated.dependency.DependsOnPackage;
import pw.stamina.munition.feature.core.Plugin;
import pw.stamina.munition.feature.core.dependency.ClassDependency;
import pw.stamina.munition.feature.core.dependency.Dependency;
import pw.stamina.munition.feature.core.dependency.FeatureDependency;
import pw.stamina.munition.feature.core.dependency.PackageDependency;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mark Johnson
 */
public abstract class AnnotatedPlugin extends AnnotatedFeature implements Plugin {

    private final Set<Dependency<?>> dependencies;

    protected AnnotatedPlugin() {
        this.dependencies = Stream.of(getClass().getDeclaredAnnotation(Dependencies.class))
                .filter(Objects::nonNull)
                .flatMap(dependencies -> Stream.of(
                        getFeatureDependencies(dependencies.features()),
                        getClassDependencies(dependencies.classes()),
                        getPackageDependencies(dependencies.packages())))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Dependency<?>> getDependencies() {
        return dependencies;
    }

    private static List<Dependency<?>> getFeatureDependencies(final DependsOnFeature[] dependencies) {
        return Arrays.stream(dependencies)
                .map(dep -> new FeatureDependency(dep.label(), dep.bundle(),
                        Version.of(dep.min().major(), dep.min().minor(), dep.min().patch(), dep.min().tags()),
                        Version.of(dep.max().major(), dep.max().minor(), dep.max().patch(), dep.max().tags())))
                .collect(Collectors.toList());
    }

    private static List<Dependency<?>> getClassDependencies(final DependsOnClass[] dependencies) {
        return Arrays.stream(dependencies)
                .map(dep -> new ClassDependency(dep.value(), Arrays.stream(dep.methods())
                        .map(methodDep -> new ClassDependency.MethodRequirement(methodDep.name(), methodDep.parameters()))
                        .toArray(ClassDependency.MethodRequirement[]::new)))
                .collect(Collectors.toList());
    }

    private static List<Dependency<?>> getPackageDependencies(final DependsOnPackage[] dependencies) {
        return Arrays.stream(dependencies)
                .map(dep -> new PackageDependency(dep.value()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AnnotatedPlugin that = (AnnotatedPlugin) o;
        return Objects.equals(getLabel(), that.getLabel()) &&
                Objects.equals(getVersion(), that.getVersion()) &&
                Objects.equals(getBundle(), that.getBundle()) &&
                Objects.equals(findMetadata(), that.findMetadata()) &&
                Objects.equals(dependencies, that.dependencies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dependencies);
    }

    @Override
    public String toString() {
        return "AnnotatedPlugin{" +
                "label='" + getLabel() + '\'' +
                ", version=" + getVersion() +
                ", bundle='" + getBundle() + '\'' +
                ", metadata=" + findMetadata().map(Object::toString).orElse("none") +
                ", dependencies=" + dependencies +
                '}';
    }
}
