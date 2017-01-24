package pw.stamina.munition.event.core;

import java.util.Objects;

/**
 * @author Mark Johnson
 */
public class Event<T> {

    private T data;

    private Event(final T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public static <T> Event<T> wrap(final T data) {
        return new Event<>(data);
    }

    @SuppressWarnings("unchecked")
    public static <T> Event<T> empty() {
        return new Event<>(null);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Event<?> event = (Event<?>) o;
        return Objects.equals(data, event.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return "Event{" +
                "data=" + data +
                '}';
    }
}
