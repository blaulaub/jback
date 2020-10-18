package ch.patchcode.jback.jpa.entities;

import java.util.Optional;
import java.util.UUID;

public interface DojoJpaRepository {

    <S extends DojoJpa> S save(S s);

    Optional<DojoJpa> findById(UUID uuid);
}
