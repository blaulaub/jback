package ch.patchcode.jback.jpa.entities;

import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.securityEntities.authentications.PersonalAuthentication;

import java.util.Optional;
import java.util.UUID;

public interface PersonalAuthenticationJpaRepository {

    <S extends PersonalAuthenticationJpa> S save(S s);

    Optional<PersonalAuthenticationJpa> findById(UUID uuid);

    default PersonalAuthenticationJpa toJpaIfConsistent(PersonalAuthentication authentication) {

        var authenticationJpa = this.findById(authentication.getId());
        if (authenticationJpa.isEmpty()) {
            throw new RuntimeException("authentication not found");
        }
        if (authenticationJpa.map(PersonalAuthenticationJpa::toDomain)
                .filter(it -> it.equals(authentication))
                .isEmpty()) {
            throw new RuntimeException("authentication mismatch (outdated?)");
        }
        return authenticationJpa.get();
    }
}
