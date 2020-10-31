package ch.patchcode.jback.jpa.entitiesSpring;

import ch.patchcode.jback.jpa.entities.PersonalAuthenticationJpa;
import ch.patchcode.jback.securityEntities.authentications.PersonalAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonalAuthenticationJpaRepository extends JpaRepository<PersonalAuthenticationJpa, UUID> {

    @Override
    <S extends PersonalAuthenticationJpa> S save(S s);

    @Override
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
