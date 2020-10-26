package ch.patchcode.jback.jpa.entities;

import java.util.Optional;
import java.util.UUID;

public interface ClubMembershipApplicationJpaRepository {

    <S extends ClubMembershipApplicationJpa> S save(S s);

    Optional<ClubMembershipApplicationJpa> findById(UUID uuid);
}
