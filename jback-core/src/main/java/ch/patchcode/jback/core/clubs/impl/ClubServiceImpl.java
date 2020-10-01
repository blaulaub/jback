package ch.patchcode.jback.core.clubs.impl;

import ch.patchcode.jback.core.clubs.ClubService;
import ch.patchcode.jback.core.entities.Club;
import ch.patchcode.jback.core.entities.ClubRepository;

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
}
