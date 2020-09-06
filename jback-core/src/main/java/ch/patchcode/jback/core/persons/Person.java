package ch.patchcode.jback.core.persons;

import ch.patchcode.jback.core.common.Address;
import org.inferred.freebuilder.FreeBuilder;

import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Person extends ch.patchcode.jback.secBase.secModelImpl.Person {

    UUID getId();

    String getFirstName();

    String getLastName();

    Optional<Address> getAddress();

    class Builder extends Person_Builder {
    }

    @FreeBuilder
    interface Draft {

        String getFirstName();

        String getLastName();

        Optional<Address> getAddress();

        class Builder extends Person_Draft_Builder {
        }
    }
}
