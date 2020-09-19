package ch.patchcode.jback.security.authentications;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.secBase.secModelImpl.Authority;
import ch.patchcode.jback.security.Principal;
import ch.patchcode.jback.security.secBaseImpl.VerificationMean;
import org.inferred.freebuilder.FreeBuilder;

import java.util.List;

import static java.util.Collections.singletonList;

@FreeBuilder
public interface PersonalAuthentication extends Principal {

    static PersonalAuthentication of(
            Person<VerificationMean> holder,
            Iterable<VerificationMean> means
    ) {
        return new Builder()
                .setHolder(holder)
                .addAllMeans(means)
                .build();
    }

    Person<VerificationMean> getHolder();

    // impl ch.patchcode.jback.util.WithFirstAndLastName

    @Override
    default String getFirstName() {

        return getHolder().getFirstName();
    }

    @Override
    default String getLastName() {

        return getHolder().getLastName();
    }

    // impl java.security.Principal

    @Override
    default String getName() {

        return getHolder().getName();
    }

    // impl ch.patchcode.jback.secBase.secModelImpl.Principal

    @Override
    List<VerificationMean> getMeans();

    @Override
    default List<Authority> getBasicPrivileges() {

        return singletonList(Authority.CAN_CREATE_CLIENT_PERSON);
    }

    class Builder extends PersonalAuthentication_Builder {
    }

    @FreeBuilder
    interface Draft {

        Person<VerificationMean> getHolder();

        List<VerificationMean> getMeans();

        class Builder extends PersonalAuthentication_Draft_Builder {
        }
    }
}
