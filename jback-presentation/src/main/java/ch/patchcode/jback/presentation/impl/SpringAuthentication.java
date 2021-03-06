package ch.patchcode.jback.presentation.impl;

import ch.patchcode.jback.coreEntities.Authority;
import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.coreEntities.roles.Role;
import ch.patchcode.jback.presentation.ApiAuthority;
import ch.patchcode.jback.securityEntities.authentications.Principal;
import ch.patchcode.jback.securityEntities.verificationMeans.VerificationMean;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class SpringAuthentication<T extends Principal>
        implements Principal, org.springframework.security.core.Authentication {

    private final T principal;

    private final Role role;

    /**
     * An authentication with no role.
     */
    public SpringAuthentication(T principal) {

        this.principal = principal;
        this.role = null;
    }

    /**
     * An authentication with a particular role.
     */
    public SpringAuthentication(T principal, Role role) {

        if (!principal.getPersons().contains(role.getPerson())) {
            throw new IllegalArgumentException("role not covered by principal");
        }

        this.principal = principal;
        this.role = role;
    }

    public static <T extends Principal> SpringAuthentication<T> of(T principal) {

        return new SpringAuthentication<>(principal);
    }

    public Optional<Role> getRole() {

        return Optional.ofNullable(role);
    }

    // from ch.patchcode.jback.securityEntities.authentications.Principal

    @Override
    public String getFirstName() {

        return principal.getFirstName();
    }

    @Override
    public String getLastName() {

        return principal.getLastName();
    }

    @Override
    public void accept(Visitor visitor) {

        principal.accept(visitor);
    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {

        return principal.accept(visitor);
    }

    // from java.security.Principal

    @Override
    public String getName() {

        return principal.getName();
    }

    // from ch.patchcode.jback.secModel.Principal

    @Override
    public List<VerificationMean> getMeans() {

        return principal.getMeans();
    }

    @Override
    public List<Person> getPersons() {

        return principal.getPersons();
    }

    @Override
    public List<Authority> getBasicPrivileges() {

        return principal.getBasicPrivileges();
    }

    // impl org.springframework.security.core.Authentication

    @Override
    public Collection<ApiAuthority> getAuthorities() {

        return Stream
                .of(
                        principal.getBasicPrivileges().stream(),
                        Optional.ofNullable(role).map(Role::getPrivileges).stream().flatMap(Collection::stream)
                )
                .flatMap(it -> it)
                .distinct()
                .map(ApiAuthority::of)
                .collect(toSet());
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
    public T getPrincipal() {
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
}
