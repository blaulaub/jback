package ch.patchcode.jback.coreEntities;

import org.inferred.freebuilder.FreeBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public final class Person implements ch.patchcode.jback.secModel.Person, Serializable {

    private final UUID id;

    private final String firstName;

    private final String lastName;

    private final Address address;

    public Person(UUID id, String firstName, String lastName, Address address) {
        this.id = requireNonNull(id);
        this.firstName = requireNonNull(firstName);
        this.lastName = requireNonNull(lastName);
        this.address = address;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    // from secModel ch.patchcode.jback.coreEntities.Person

    public String getName() {

        return getFirstName() + " " + getLastName();
    }

    @FreeBuilder
    public interface Draft {

        String getFirstName();

        String getLastName();

        Optional<Address> getAddress();

        List<Authority> getExtraPrivileges();

        class Builder extends Person_Draft_Builder {
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id.equals(person.id) &&
                firstName.equals(person.firstName) &&
                lastName.equals(person.lastName) &&
                Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, address);
    }
}
