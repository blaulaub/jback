package ch.patchcode.jback.jpa.verificationMeans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationMeanJpaRepository extends JpaRepository<VerificationMeanJpa, UUID> {

    @Override
    <S extends VerificationMeanJpa> S save(S s);

    @Override
    Optional<VerificationMeanJpa> findById(UUID uuid);
}
