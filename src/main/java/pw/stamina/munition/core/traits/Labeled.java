package pw.stamina.munition.core.traits;

/**
 * @author Mark Johnson
 */
public interface Labeled {

    String getLabel();

    interface Mutably extends Labeled {
        void setLabel(String label);
    }
}
