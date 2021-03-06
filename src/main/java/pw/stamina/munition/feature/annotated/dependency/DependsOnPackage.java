package pw.stamina.munition.feature.annotated.dependency;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Mark Johnson
 */
@Retention(RetentionPolicy.CLASS)
public @interface DependsOnPackage {
    String value();
}