package ch.patchcode.jback.jpa.entities;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VerificationMeanJpaRepository {

    <S extends VerificationMeanJpa> S save(S s);

    <S extends VerificationMeanJpa> List<S> saveAll(Iterable<S> iterable);

    Optional<VerificationMeanJpa> findById(UUID uuid);
}
