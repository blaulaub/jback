package ch.patchcode.jback.jpa.entities;

import java.util.Optional;
import java.util.UUID;

public interface VerificationMeanJpaRepository {

    <S extends VerificationMeanJpa> S save(S s);

    Optional<VerificationMeanJpa> findById(UUID uuid);
}
