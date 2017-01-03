package pw.stamina.munition.feature.core;

import pw.stamina.munition.core.traits.Toggleable;

/**
 * @author Mark Johnson
 */
public interface Module extends Feature, Toggleable {
    @Override
    void toggle();

    @Override
    boolean isEnabled();
}
