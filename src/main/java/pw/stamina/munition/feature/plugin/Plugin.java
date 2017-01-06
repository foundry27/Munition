package pw.stamina.munition.feature.plugin;

import pw.stamina.munition.core.traits.Pluggable;
import pw.stamina.munition.feature.core.Feature;
import pw.stamina.munition.feature.plugin.dependency.Dependency;

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