package ch.patchcode.jback.core.clubs;

import ch.patchcode.jback.core.entities.Club;
import ch.patchcode.jback.secBase.VerificationMean;

import java.util.Optional;
import java.util.UUID;

public interface ClubService<TVerificationMean extends VerificationMean> {

    Optional<Club<TVerificationMean>> getClub(UUID id);
}
