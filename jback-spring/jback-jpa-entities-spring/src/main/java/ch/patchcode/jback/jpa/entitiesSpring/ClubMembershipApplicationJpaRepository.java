package ch.patchcode.jback.jpa.entitiesSpring;

import ch.patchcode.jback.jpa.entities.ClubMembershipApplicationJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClubMembershipApplicationJpaRepository extends JpaRepository<ClubMembershipApplicationJpa, UUID> {

    @Override
    <S extends ClubMembershipApplicationJpa> S save(S s);

    @Override
    Optional<ClubMembershipApplicationJpa> findById(UUID uuid);

    Page<ClubMembershipApplicationJpa> findAllByIdGreaterThan(UUID id, Pageable pageable);
}
