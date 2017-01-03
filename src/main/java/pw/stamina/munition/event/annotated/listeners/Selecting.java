package pw.stamina.munition.event.annotated.listeners;

import pw.stamina.munition.event.core.selection.Selector;

/**
 * @author Mark Johnson
 */
public interface Selecting<K> {
    Selector<K> getSelector();
}
