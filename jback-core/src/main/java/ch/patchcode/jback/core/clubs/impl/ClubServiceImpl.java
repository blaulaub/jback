package ch.patchcode.jback.core.clubs.impl;

import ch.patchcode.jback.core.clubs.Club;
import ch.patchcode.jback.core.clubs.ClubRepository;
import ch.patchcode.jback.core.clubs.ClubService;
import ch.patchcode.jback.secBase.VerificationMean;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

public class ClubServiceImpl<TVerificationMeant extends VerificationMean> implements ClubService<TVerificationMeant> {

    private final ClubRepository<TVerificationMeant> clubRepository;

    @Inject
    public ClubServiceImpl(ClubRepository<TVerificationMeant> clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public Optional<Club<TVerificationMeant>> getClub(UUID id) {
        return clubRepository.findById(id);
    }
}
