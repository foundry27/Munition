package pw.stamina.munition.event.core.selection;

/**
 * @author Mark Johnson
 */
public interface Selector<SELECTOR_KEY> {
    boolean canSelect(SELECTOR_KEY key);
}
