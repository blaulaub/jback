package ch.patchcode.jback.sec.authentication;

import org.inferred.freebuilder.FreeBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

import static java.util.Collections.emptyList;

public abstract class PendingVerificationAuthentication implements Authentication {

    public abstract UUID getId();

    public abstract String getCode();

    @Override
    public final String getCredentials() {

        return getCode();
    }

    @Override
    public final Object getDetails() {

        // not a user -- no details
        return null;
    }

    @Override
    public final UUID getPrincipal() {

        return getId();
    }

    @Override
    public final String getName() {

        // not a user -- no name
        return null;
    }

    @Override
    public final void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        if (isAuthenticated != isAuthenticated()) {
            throw new RuntimeException("unmodifiable");
        }
    }

    @FreeBuilder
    public abstract static class Unchecked extends PendingVerificationAuthentication {

        public static Unchecked of(UUID id, String code) {

            return new Builder().setId(id).setCode(code).build();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {

            return emptyList();
        }

        @Override
        public boolean isAuthenticated() {

            return false;
        }

        public static class Builder extends PendingVerificationAuthentication_Unchecked_Builder {

        }
    }

    @FreeBuilder
    public abstract static class Confirmed extends PendingVerificationAuthentication {

        public static Confirmed of(UUID id, String code) {

            return new Builder().setId(id).setCode(code).build();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {

            return emptyList();
        }

        @Override
        public boolean isAuthenticated() {

            return true;
        }

        public static class Builder extends PendingVerificationAuthentication_Confirmed_Builder {

        }
    }
}
