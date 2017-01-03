package pw.stamina.munition.feature.annotated.metadata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Mark Johnson
 */
@Retention(RetentionPolicy.CLASS)
public @interface AuthorModel {
    String alias();

    String name() default "";

    String email() default "";

    String timezone() default "";
}
