package ch.patchcode.jback.coreEntities;

import org.inferred.freebuilder.FreeBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Person extends ch.patchcode.jback.secModel.Person {

    UUID getId();

    String getFirstName();

    String getLastName();

    Optional<Address> getAddress();

    // from secModel ch.patchcode.jback.coreEntities.Person

    default String getName() {

        return getFirstName() + " " + getLastName();
    }

    class Builder extends Person_Builder {
    }

    @FreeBuilder
    interface Draft {

        String getFirstName();

        String getLastName();

        Optional<Address> getAddress();

        List<Authority> getExtraPrivileges();

        class Builder extends Person_Draft_Builder {
        }
    }
}
