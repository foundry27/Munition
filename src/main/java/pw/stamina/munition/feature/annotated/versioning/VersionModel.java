package pw.stamina.munition.feature.annotated.versioning;

import pw.stamina.munition.core.versioning.VersionTag;

/**
 * @author Mark Johnson
 */
public @interface VersionModel {
    int major();

    int minor();

    int patch() default 0;

    VersionTag[] tags() default {};

    String[] meta() default {};
}
