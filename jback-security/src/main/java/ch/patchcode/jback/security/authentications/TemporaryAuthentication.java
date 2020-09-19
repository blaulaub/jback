package ch.patchcode.jback.security.authentications;

import ch.patchcode.jback.secBase.secModelImpl.Authority;
import ch.patchcode.jback.security.Authentication;
import ch.patchcode.jback.security.secBaseImpl.PendingRegistration;
import ch.patchcode.jback.security.secBaseImpl.VerificationMean;

import java.util.List;

import static java.util.Collections.singletonList;

/**
 * This is the most stupid form of an authenticated user (beside the anonymous user). What we have here is
 * someone who can verify his identity via something given by {@link #getMeans()}.
 */
public class TemporaryAuthentication implements Authentication {

    private final String firstName;
    private final String lastName;
    private final VerificationMean mean;

    public TemporaryAuthentication(String firstName, String lastName, VerificationMean mean) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.mean = mean;
    }

    public static TemporaryAuthentication of(PendingRegistration registration) {

        return new TemporaryAuthentication(
                registration.getFirstName(),
                registration.getLastName(),
                registration.getVerificationMean());
    }

    @Override
    public String getFirstName() {

        return firstName;
    }

    @Override
    public String getLastName() {

        return lastName;
    }

    // impl java.security.Principal

    @Override
    public String getName() {

        return getFirstName() + " " + getLastName();
    }

    // impl ch.patchcode.jback.secBase.secModelImpl.Principal

    @Override
    public List<VerificationMean> getMeans() {

        return singletonList(this.mean);
    }

    @Override
    public List<Authority> getBasicPrivileges() {

        return singletonList(Authority.CAN_CREATE_OWN_PERSON);
    }
}
