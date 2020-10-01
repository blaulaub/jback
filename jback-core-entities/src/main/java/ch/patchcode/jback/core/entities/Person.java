package ch.patchcode.jback.core.entities;

import ch.patchcode.jback.secBase.secModelImpl.Authority;
import ch.patchcode.jback.util.WithFirstAndLastName;
import org.inferred.freebuilder.FreeBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Person extends
        ch.patchcode.jback.secBase.secModelImpl.Person,
        WithFirstAndLastName {

    UUID getId();

    @Override
    String getFirstName();

    @Override
    String getLastName();

    Optional<Address> getAddress();

    // from secModel ch.patchcode.jback.core.entities.Person

    default String getName() {

        return getFirstName() + " " + getLastName();
    }

    class Builder extends Person_Builder {
    }

    @FreeBuilder
    interface Draft extends WithFirstAndLastName {

        @Override
        String getFirstName();

        @Override
        String getLastName();

        Optional<Address> getAddress();

        List<Authority> getExtraPrivileges();

        class Builder extends Person_Draft_Builder {
        }
    }
}
