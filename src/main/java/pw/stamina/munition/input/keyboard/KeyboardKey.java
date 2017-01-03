package pw.stamina.munition.input.keyboard;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mark Johnson
 */
public final class KeyboardKey<K> {

    private static final Map<Object, KeyboardKey<?>> KEY_CACHE = new ConcurrentHashMap<>();

    private final char characterRepresentation;

    private final K internalRepresentation;

    private KeyboardKey(final char characterRepresentation, final K internalRepresentation) {
        this.characterRepresentation = characterRepresentation;
        this.internalRepresentation = internalRepresentation;
    }

    public static <K> KeyboardKey<K> from(final char characterRepresentation, final K internalRepresentation) {
        return findOrCreateAppropriateKey(characterRepresentation, internalRepresentation);
    }

    @SuppressWarnings("unchecked")
    private static <K> KeyboardKey<K> findOrCreateAppropriateKey(final char characterRepresentation, final K internalRepresentation) {
        return (KeyboardKey<K>) KEY_CACHE.computeIfAbsent(internalRepresentation,
                rep -> new KeyboardKey<>(characterRepresentation, rep));
    }

    public char getCharacterRepresentation() {
        return characterRepresentation;
    }

    public K getInternalRepresentation() {
        return internalRepresentation;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyboardKey)) return false;
        final KeyboardKey<?> that = (KeyboardKey<?>) o;
        return getCharacterRepresentation() == that.getCharacterRepresentation() &&
                Objects.equals(getInternalRepresentation(), that.getInternalRepresentation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCharacterRepresentation(), getInternalRepresentation());
    }

    @Override
    public String toString() {
        return "KeyboardKey{" +
                "character='" + characterRepresentation + "'" +
                ", internally=" + internalRepresentation +
                '}';
    }
}