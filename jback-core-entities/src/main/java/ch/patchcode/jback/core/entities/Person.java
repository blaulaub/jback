package ch.patchcode.jback.core.entities;

import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.secBase.secModelImpl.Authority;
import ch.patchcode.jback.secBase.secModelImpl.Principal;
import ch.patchcode.jback.secBase.secModelImpl.Role;
import ch.patchcode.jback.util.WithFirstAndLastName;
import org.inferred.freebuilder.FreeBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Person<TVerificationMean extends VerificationMean> extends
        ch.patchcode.jback.secBase.secModelImpl.Person<TVerificationMean>,
        WithFirstAndLastName {

    UUID getId();

    @Override
    String getFirstName();

    @Override
    String getLastName();

    Optional<Address> getAddress();

    // from secModel ch.patchcode.jback.core.entities.Person

    @Override
    List<Principal<TVerificationMean>> getPrincipals();

    @Override
    List<Authority> getExtraPrivileges();

    default String getName() {

        return getFirstName() + " " + getLastName();
    }

    class Builder<TVerificationMean extends VerificationMean> extends Person_Builder<TVerificationMean> {
    }

    @FreeBuilder
    interface Draft<TVerificationMean extends VerificationMean> extends WithFirstAndLastName {

        @Override
        String getFirstName();

        @Override
        String getLastName();

        Optional<Address> getAddress();

        List<Role<TVerificationMean>> getRoles();

        List<Authority> getExtraPrivileges();

        class Builder<TVerificationMean extends VerificationMean> extends Person_Draft_Builder<TVerificationMean> {
        }
    }
}
