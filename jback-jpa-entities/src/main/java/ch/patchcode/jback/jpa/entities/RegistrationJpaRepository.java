package ch.patchcode.jback.jpa.entities;

import java.util.Optional;
import java.util.UUID;

public interface RegistrationJpaRepository {

    <S extends RegistrationJpa> S save(S s);

    Optional<RegistrationJpa> findById(UUID uuid);

    void deleteById(UUID uuid);
}
