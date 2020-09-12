package ch.patchcode.jback.security.authentications;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.secBase.secModelImpl.Authority;
import ch.patchcode.jback.security.Authentication;
import com.google.common.collect.ImmutableList;

import java.util.List;

import static java.util.Collections.singletonList;

public class PersonalAuthentication implements Authentication {

    private final Person holder;
    private final ImmutableList<VerificationMean> means;

    public PersonalAuthentication(
            Person holder,
            Iterable<VerificationMean> means
    ) {
        this.holder = holder;
        this.means = ImmutableList.copyOf(means);
    }

    public Person getHolder() {
        return holder;
    }

    // impl ch.patchcode.jback.secBase.secModelImpl.Principal

    @Override
    public List<VerificationMean> getMeans() {
        return means;
    }

    @Override
    public List<Authority> getBasicPrivileges() {

        return singletonList(Authority.CAN_CREATE_CLIENT_PERSON);
    }
}
