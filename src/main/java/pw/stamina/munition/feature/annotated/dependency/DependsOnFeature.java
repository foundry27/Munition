package pw.stamina.munition.feature.annotated.dependency;

import pw.stamina.munition.feature.annotated.versioning.VersionModel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Mark Johnson
 */
@Retention(RetentionPolicy.CLASS)
public @interface DependsOnFeature {
    String label();

    String bundle();

    VersionModel min() default @VersionModel(major = 0, minor = 0, patch = 0);

    VersionModel max() default @VersionModel(major = Integer.MAX_VALUE, minor = Integer.MAX_VALUE, patch = Integer.MAX_VALUE);
}
