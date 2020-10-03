package ch.patchcode.jback.jpa.entitiesSpring;

import ch.patchcode.jback.jpa.entities.VerificationMeanJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationMeanJpaRepository extends
        JpaRepository<VerificationMeanJpa, UUID>,
        ch.patchcode.jback.jpa.entities.VerificationMeanJpaRepository {

    @Override
    <S extends VerificationMeanJpa> S save(S s);

    @Override
    Optional<VerificationMeanJpa> findById(UUID uuid);
}
