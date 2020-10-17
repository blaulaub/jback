package ch.patchcode.jback.jpa.entities;

import java.util.Optional;
import java.util.UUID;

public interface RoleJpaRepository {

    <S extends RoleJpa> S save(S s);

    Optional<RoleJpa> findById(UUID uuid);
}
