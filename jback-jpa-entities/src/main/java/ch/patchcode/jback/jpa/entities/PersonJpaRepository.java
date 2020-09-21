package ch.patchcode.jback.jpa.entities;

import java.util.Optional;
import java.util.UUID;

public interface PersonJpaRepository {

    <S extends PersonJpa> S save(S s);

    Optional<PersonJpa> findById(UUID uuid);
}
