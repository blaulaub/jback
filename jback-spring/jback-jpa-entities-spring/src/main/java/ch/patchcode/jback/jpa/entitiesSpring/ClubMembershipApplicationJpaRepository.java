package ch.patchcode.jback.jpa.entitiesSpring;

import ch.patchcode.jback.jpa.entities.ClubMembershipApplicationJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClubMembershipApplicationJpaRepository extends
        JpaRepository<ClubMembershipApplicationJpa, UUID>,
        ch.patchcode.jback.jpa.entities.ClubMembershipApplicationJpaRepository {

    @Override
    <S extends ClubMembershipApplicationJpa> S save(S s);

    @Override
    Optional<ClubMembershipApplicationJpa> findById(UUID uuid);
}
