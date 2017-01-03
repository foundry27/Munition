package pw.stamina.munition.core.traits;

/**
 * @author Mark Johnson
 */
public interface Pluggable {
    void load();

    void unload();

    boolean isLoaded();

    default void reload() {
        unload();
        load();
    };
}
