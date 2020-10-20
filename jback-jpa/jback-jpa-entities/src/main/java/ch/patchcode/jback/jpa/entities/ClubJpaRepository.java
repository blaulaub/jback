package ch.patchcode.jback.jpa.entities;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClubJpaRepository {

    <S extends ClubJpa> S save(S s);

    Optional<ClubJpa> findById(UUID uuid);

    List<ClubJpa> findAllLimitedTo(int count);

    List<ClubJpa> findAllByNameContainingLimitedTo(String pattern, int count);
}
