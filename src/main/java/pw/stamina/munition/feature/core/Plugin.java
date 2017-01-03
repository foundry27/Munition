package pw.stamina.munition.feature.core;

import pw.stamina.munition.core.traits.Pluggable;
import pw.stamina.munition.feature.core.dependency.Dependency;

import java.util.Set;

/**
 * @author Mark Johnson
 */
public interface Plugin extends Feature, Pluggable {
    @Override
    void load();

    @Override
    void unload();

    @Override
    boolean isLoaded();

    @Override
    default void reload() {
        unload();
        load();
    };

    Set<Dependency<?>> getDependencies();
}