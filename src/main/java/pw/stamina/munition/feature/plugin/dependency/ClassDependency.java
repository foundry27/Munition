package pw.stamina.munition.feature.plugin.dependency;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Mark Johnson
 */
public class ClassDependency extends AbstractAutomaticLookupDependency<ClassLoader> {

    private final String canonicalClassName;

    private final MethodRequirement[] methodDependencies;

    public ClassDependency(final String canonicalClassName, final MethodRequirement... methodDependencies) {
        this.canonicalClassName = Objects.requireNonNull(canonicalClassName, "Canonical class name cannot be null");
        this.methodDependencies = Objects.requireNonNull(methodDependencies, "Method dependency lists cannot be null");
    }

    @Override
    protected void tryResolvingWithLookup(final ClassLoader lookup) {
        try {
            final Class<?> classLookup = Class.forName(canonicalClassName, false, lookup);
            for (final MethodRequirement methodRequirement : methodDependencies) {
                methodRequirement.tryResolving(classLookup);
            }
            resolutionStatus = ResolutionStatus.RESOLVED;
        } catch (final ClassNotFoundException e) {
            resolutionStatus = ResolutionStatus.FAILED;
            throw new DependencyResolutionException(String.format("No class named \"%s\" exists within the domain of the specified classloader.", canonicalClassName));
        } catch (final DependencyResolutionException e) {
            resolutionStatus = ResolutionStatus.FAILED;
            throw new DependencyResolutionException(String.format("Encountered exception while parsing method dependencies for class %s: %s",
                    canonicalClassName, e.getMessage()));
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ClassDependency that = (ClassDependency) o;
        return Objects.equals(canonicalClassName, that.canonicalClassName) &&
                Arrays.equals(methodDependencies, that.methodDependencies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(canonicalClassName, methodDependencies);
    }

    @Override
    public String toString() {
        return "ClassDependency{" +
                "canonicalClassName='" + canonicalClassName + '\'' +
                ", methodDependencies=" + Arrays.toString(methodDependencies) +
                '}';
    }

    public static class MethodRequirement {

        private final String methodName;

        private final Class<?>[] methodParameters;

        public MethodRequirement(final String methodName, final Class<?>... methodParameters) {
            this.methodName = methodName;
            this.methodParameters = methodParameters;
        }

        public void tryResolving(final Class<?> aClass) throws DependencyResolutionException {
            try {
                final Method methodLookup = aClass.getDeclaredMethod(methodName, methodParameters);
            } catch (final NoSuchMethodException e) {
                throw new DependencyResolutionException(String.format("Class %s does not have a method named \"%s\" with the parameters %s",
                        aClass.getCanonicalName(), methodName, Arrays.toString(methodParameters)));
            }
        }

        @Override
        public String toString() {
            return "MethodRequirement{" +
                    "name='" + methodName + '\'' +
                    ", parameters=" + Arrays.toString(methodParameters) +
                    '}';
        }
    }
}
