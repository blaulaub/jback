package ch.patchcode.jback.core.entities;

import ch.patchcode.jback.secBase.VerificationMean;

import java.util.Optional;
import java.util.UUID;

public interface ClubRepository<TVerificationMean extends VerificationMean> {

    Optional<Club<TVerificationMean>> findById(UUID id);

    Club<TVerificationMean> create(Club.Draft<TVerificationMean> draft);
}
