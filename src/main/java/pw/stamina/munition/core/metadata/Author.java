package pw.stamina.munition.core.metadata;

import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;

/**
 * @author Mark Johnson
 */
public final class Author {

    private final String alias;

    private final String name;

    private final String email;

    private final TimeZone timeZone;

    public Author(final String alias, final String name, final String email, final TimeZone timeZone) {
        this.alias = Objects.requireNonNull(alias, "Author aliases cannot be null");
        this.name = name;
        this.email = email;
        this.timeZone = timeZone;
    }

    public Author(final String alias, final String name, final String email) {
        this(alias, name, email, null);
    }

    public Author(final String alias, final String name) {
        this(alias, name, null, null);
    }

    public Author(final String alias) {
        this(alias, null, null, null);
    }


    public String getAlias() {
        return alias;
    }

    public Optional<String> findName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> findEmail() {
        return Optional.ofNullable(email);
    }

    public Optional<TimeZone> findTimeZone() {
        return Optional.ofNullable(timeZone);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Author author = (Author) o;
        return Objects.equals(alias, author.alias) &&
                Objects.equals(name, author.name) &&
                Objects.equals(email, author.email) &&
                Objects.equals(timeZone, author.timeZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias, name, email, timeZone);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("Author{alias=%s", alias));
        if (name != null) {
            sb.append(String.format(", name=%s", name));
        }
        if (email != null) {
            sb.append(String.format(", email=%s", email));
        }
        if (timeZone != null) {
            sb.append(String.format(", timezone=%s", timeZone.getDisplayName()));
        }
        sb.append("}");
        return sb.toString();
    }
}
