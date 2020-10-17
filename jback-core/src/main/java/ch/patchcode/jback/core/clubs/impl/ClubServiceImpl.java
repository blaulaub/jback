package ch.patchcode.jback.core.clubs.impl;

import ch.patchcode.jback.core.clubs.ClubService;
import ch.patchcode.jback.coreEntities.Club;
import ch.patchcode.jback.coreEntities.ClubRepository;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;

    @Inject
    public ClubServiceImpl(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public Optional<Club> getClub(UUID id) {
        return clubRepository.findById(id);
    }

    @Override
    public Club create(Club.Draft draft) {

        throw new RuntimeException("not implemented");
    }
}
