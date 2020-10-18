package ch.patchcode.jback.jpa.entitiesSpring;

import ch.patchcode.jback.jpa.entities.DojoJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DojoJpaRepository extends
        JpaRepository<DojoJpa, UUID>,
        ch.patchcode.jback.jpa.entities.DojoJpaRepository {

    @Override
    <S extends DojoJpa> S save(S s);

    @Override
    Optional<DojoJpa> findById(UUID uuid);
}
