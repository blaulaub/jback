package ch.patchcode.jback.core.persons;

import ch.patchcode.jback.core.common.Address;
import ch.patchcode.jback.secBase.secModelImpl.Authority;
import ch.patchcode.jback.secBase.secModelImpl.Principal;
import ch.patchcode.jback.secBase.secModelImpl.Role;
import ch.patchcode.jback.util.WithFirstAndLastName;
import org.inferred.freebuilder.FreeBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Person extends ch.patchcode.jback.secBase.secModelImpl.Person, WithFirstAndLastName {

    UUID getId();

    @Override
    String getFirstName();

    @Override
    String getLastName();

    Optional<Address> getAddress();

    // from secModel Person

    @Override
    List<Principal> getPrincipals();

    @Override
    List<Role> getRoles();

    @Override
    List<Authority> getExtraPrivileges();

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

        List<Role> getRoles();

        List<Authority> getExtraPrivileges();

        class Builder extends Person_Draft_Builder {
        }
    }
}
