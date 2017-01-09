package pw.stamina.munition.core;

import pw.stamina.munition.core.metadata.Author;
import pw.stamina.munition.core.versioning.Version;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Mark Johnson
 */
public class ImmutableExtensionDescriptorDecorator implements ExtensionDescriptor {

    private final ExtensionDescriptor backingDescriptor;

    public ImmutableExtensionDescriptorDecorator(final ExtensionDescriptor backingDescriptor) {
        this.backingDescriptor = Objects.requireNonNull(backingDescriptor, "The backing extension descriptor cannot be null");
    }

    @Override
    public String getLabel() {
        return backingDescriptor.getLabel();
    }

    @Override
    public String getBundle() {
        return backingDescriptor.getBundle();
    }

    @Override
    public List<Author> getAuthors() {
        return Collections.unmodifiableList(backingDescriptor.getAuthors());
    }

    @Override
    public Version getVersion() {
        return backingDescriptor.getVersion();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ExtensionDescriptor)) return false;
        final ExtensionDescriptor that = (ExtensionDescriptor) o;
        return Objects.equals(backingDescriptor, that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(backingDescriptor);
    }

    @Override
    public String toString() {
        return "ImmutableExtensionDescriptorDecorator{" +
                "backingDescriptor=" + backingDescriptor +
                '}';
    }
}
