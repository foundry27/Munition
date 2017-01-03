package pw.stamina.munition.feature.annotated.dependency;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mark Johnson
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dependencies {
    DependsOnFeature[] features() default {};

    DependsOnClass[] classes() default {};

    DependsOnPackage[] packages() default {};
}
