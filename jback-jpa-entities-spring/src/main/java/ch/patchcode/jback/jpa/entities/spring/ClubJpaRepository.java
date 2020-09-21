package ch.patchcode.jback.jpa.entities.spring;

import ch.patchcode.jback.jpa.entities.ClubJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClubJpaRepository extends
        JpaRepository<ClubJpa, UUID>,
        ch.patchcode.jback.jpa.entities.ClubJpaRepository {

    @Override
    <S extends ClubJpa> S save(S s);

    @Override
    Optional<ClubJpa> findById(UUID uuid);
}
