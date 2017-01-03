package pw.stamina.munition.feature.annotated;

import pw.stamina.munition.feature.annotated.metadata.MetadataModel;
import pw.stamina.munition.feature.annotated.versioning.VersionModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mark Johnson
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FeatureModel {
    String label();

    VersionModel version();

    String bundle();

    MetadataModel meta() default @MetadataModel;
}
