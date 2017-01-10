package pw.stamina.munition.event.annotated.field.listeners;

import pw.stamina.munition.event.core.selection.Selector;

/**
 * @author Mark Johnson
 */
public interface Selective<K> {
    Selector<K> getSelector();
}
