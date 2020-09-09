package ch.patchcode.jback.security.authentications;

import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.secBase.secModelImpl.Authority;
import ch.patchcode.jback.security.authorities.ApiAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public class VerifyablePrincipal implements Authentication, ch.patchcode.jback.secBase.secModelImpl.Principal {

    private final String principal;

    // TODO use immutable type
    private final List<VerificationMean> means;

    // this may not be the final constructor
    public VerifyablePrincipal(String principal, List<VerificationMean> means) {
        this.principal = principal;
        this.means = means;
    }

    // impl java.security.Principal

    @Override
    public String getName() {
        return null;
    }

    // impl org.springframework.security.core.Authentication


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // TODO maybe should consider person's extra authorities, too
        return getBasicPrivileges().stream().map(ApiAuthority::of).collect(toList());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
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
        return means;
    }

    @Override
    public List<Authority> getBasicPrivileges() {

        return singletonList(Authority.CAN_CREATE_PERSON);
    }
}
