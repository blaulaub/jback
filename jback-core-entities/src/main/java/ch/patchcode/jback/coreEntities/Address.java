package ch.patchcode.jback.coreEntities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static java.util.List.copyOf;

public final class Address implements Serializable {

    private final List<String> lines;

    public Address(List<String> lines) {
        this.lines = copyOf(lines);
    }

    public List<String> getLines() {
        return lines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return lines.equals(address.lines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lines);
    }
}
