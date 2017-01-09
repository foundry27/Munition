package pw.stamina.munition.feature.plugin.dependency;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Objects;

/**
 * @author Mark Johnson
 */
public class PackageDependency extends AbstractAutomaticLookupDependency<ClassLoader> {

    private static final Method PACKAGE_FINDER_METHOD;

    private final String packageName;

    public PackageDependency(final String packageName) {
        this.packageName = Objects.requireNonNull(packageName, "The package name cannot be null");
    }

    @Override
    protected void tryResolvingWithLookup(final ClassLoader lookup) {
        try {
            resolutionStatus = PACKAGE_FINDER_METHOD.invoke(lookup, packageName) != null ? ResolutionStatus.RESOLVED : ResolutionStatus.FAILED;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PackageDependency that = (PackageDependency) o;
        return Objects.equals(packageName, that.packageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageName);
    }

    @Override
    public String toString() {
        return "PackageDependency{" +
                "packageName='" + packageName + '\'' +
                '}';
    }

    static {
        try {
            final Method packageFinderMethod = ClassLoader.class.getDeclaredMethod("getPackage", String.class);
            AccessController.doPrivileged((PrivilegedAction<Void>)() -> {
                packageFinderMethod.setAccessible(true);
                return null;
            });
            PACKAGE_FINDER_METHOD = packageFinderMethod;
        } catch (final ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
