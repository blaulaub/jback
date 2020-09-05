package ch.patchcode.jback.security;

import ch.patchcode.jback.secBase.PendingRegistration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class TemporaryAuthentication implements Authentication {

    private final String principal;
    private final String code;
    private final String userName;

    public TemporaryAuthentication(PendingRegistration.Id id, PendingRegistration registration) {

        this.principal = id.getId().toString();
        this.code = registration.getVerificationCode();
        this.userName = String.join(" ", registration.getFirstName(), registration.getLastName());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {

        return code;
    }

    @Override
    public Object getDetails() {

        return userName;
    }

    @Override
    public Object getPrincipal() {

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

    @Override
    public String getName() {

        return principal;
    }
}
