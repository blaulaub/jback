package ch.patchcode.jback.jpa.entities;

import java.util.Optional;
import java.util.UUID;

public interface ClubJpaRepository {

    <S extends ClubJpa> S save(S s);

    Optional<ClubJpa> findById(UUID uuid);
}
