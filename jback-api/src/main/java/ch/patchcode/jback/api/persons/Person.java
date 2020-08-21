package ch.patchcode.jback.api.persons;

import ch.patchcode.jback.api.common.Address;
import org.inferred.freebuilder.FreeBuilder;

import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Person {

    UUID getId();
    String getFirstName();
    String getLastName();
    Optional<Address> getAddress();

    class Builder extends Person_Builder {}
}
