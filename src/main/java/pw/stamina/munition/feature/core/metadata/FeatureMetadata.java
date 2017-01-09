package pw.stamina.munition.feature.core.metadata;

import pw.stamina.munition.core.metadata.Author;
import pw.stamina.munition.core.metadata.Authored;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mark Johnson
 */
public final class FeatureMetadata implements Authored {

    private final List<Author> authors;

    private final String description;

    public FeatureMetadata(final List<Author> authors, final String description) {
        this.authors = Collections.unmodifiableList(Objects.requireNonNull(authors, "The authors list cannot be null"));
        this.description = description;
    }

    public FeatureMetadata(final List<Author> authors) {
        this(authors, null);
    }

    @Override
    public List<Author> getAuthors() {
        return authors;
    }

    public Optional<String> findDescription() {
        return Optional.ofNullable(description)
                .filter(description -> !description.isEmpty());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FeatureMetadata that = (FeatureMetadata) o;
        return Objects.equals(authors, that.authors) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authors, description);
    }

    @Override
    public String toString() {
        return "FeatureMetadata{" +
                "authors=" + authors +
                ", description='" + description + '\'' +
                '}';
    }
}
