package ch.patchcode.jback.security;

import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.secBase.secModelImpl.Authority;
import ch.patchcode.jback.security.authorities.ApiAuthority;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public interface Authentication extends org.springframework.security.core.Authentication, ch.patchcode.jback.secBase.secModelImpl.Principal {

    // impl java.security.Principal

    @Override
    String getName();

    // impl org.springframework.security.core.Authentication

    @Override
    default Collection<ApiAuthority> getAuthorities() {

        return getBasicPrivileges().stream().map(ApiAuthority::of).collect(toList());
    }

    @Override
    Object getCredentials();

    @Override
    Object getDetails();

    @Override
    String getPrincipal();

    @Override
    default boolean isAuthenticated() {

        return true;
    }

    @Override
    default void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        throw new IllegalArgumentException("immutable");
    }

    // impl ch.patchcode.jback.secBase.secModelImpl.Principal

    @Override
    List<VerificationMean> getMeans();

    @Override
    List<Authority> getBasicPrivileges();
}
