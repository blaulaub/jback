package ch.patchcode.jback.jpa.entitiesSpring;

import ch.patchcode.jback.jpa.entities.VerificationMeanJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationMeanJpaRepository extends JpaRepository<VerificationMeanJpa, UUID> {

    @Override
    <S extends VerificationMeanJpa> S save(S s);

    @Override
    <S extends VerificationMeanJpa> List<S> saveAll(Iterable<S> iterable);

    @Override
    Optional<VerificationMeanJpa> findById(UUID uuid);
}
