package ch.patchcode.jback.jpa.entities;

import java.util.Optional;
import java.util.UUID;

public interface PersonalAuthenticationJpaRepository {

    <S extends PersonalAuthenticationJpa> S save(S s);

    Optional<PersonalAuthenticationJpa> findById(UUID uuid);
}
