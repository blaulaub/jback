package ch.patchcode.jback.presentation;

import ch.patchcode.jback.security.authorities.ApiAuthority;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

public interface Authentication extends
        ch.patchcode.jback.security.Principal,
        org.springframework.security.core.Authentication {

    // impl org.springframework.security.core.Authentication

    @Override
    default Collection<ApiAuthority> getAuthorities() {

        return getBasicPrivileges().stream().map(ApiAuthority::of).collect(toList());
    }

    @Override
    default Object getCredentials() {

        return null;
    }

    @Override
    default Object getDetails() {

        return null;
    }

    @Override
    default Object getPrincipal() {

        return null;
    }

    @Override
    default boolean isAuthenticated() {

        return true;
    }

    @Override
    default void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        throw new IllegalArgumentException("immutable");
    }
}
