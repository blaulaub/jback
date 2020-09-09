package ch.patchcode.jback.security.authentications;

import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.secBase.secModelImpl.Authority;
import ch.patchcode.jback.security.authorities.ApiAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

/**
 * This is the most stupid form of an authenticated user (beside the anonymous user). What we have here is
 * someone who can verify his identity via something given by {@link #getMeans()}.
 */
public class TemporaryAuthentication implements Authentication, ch.patchcode.jback.secBase.secModelImpl.Principal {

    private final String principal;
    private final String code;
    private final String firstName;
    private final String lastName;
    private final VerificationMean mean;

    public TemporaryAuthentication(PendingRegistration.Id id, PendingRegistration registration) {

        this.principal = id.getId().toString();
        this.code = registration.getVerificationCode();
        this.firstName = registration.getFirstName();
        this.lastName = registration.getLastName();
        this.mean = registration.getVerificationMean();
    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {

        return lastName;
    }

    // impl java.security.Principal

    @Override
    public String getName() {

        return principal;
    }

    // impl org.springframework.security.core.Authentication

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return getBasicPrivileges().stream().map(ApiAuthority::of).collect(toList());
    }

    @Override
    public Object getCredentials() {

        return code;
    }

    @Override
    public Object getDetails() {

        return String.join(" ", firstName, lastName);
    }

    @Override
    public String getPrincipal() {

        return principal;
    }

    @Override
    public boolean isAuthenticated() {

        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        throw new IllegalArgumentException("immutable");
    }

    // impl ch.patchcode.jback.secBase.secModelImpl.Principal

    @Override
    public List<VerificationMean> getMeans() {

        return singletonList(this.mean);
    }

    @Override
    public List<Authority> getBasicPrivileges() {

        return singletonList(Authority.CAN_CREATE_PERSON);
    }
}
