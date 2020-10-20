package ch.patchcode.jback.core.clubs.impl;

import ch.patchcode.jback.core.clubs.ClubService;
import ch.patchcode.jback.coreEntities.Club;
import ch.patchcode.jback.coreEntities.ClubRepository;

import javax.inject.Inject;
import java.util.List;
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
        return clubRepository.create(draft);
    }

    @Override
    public List<Club> find(String pattern) {
        if (pattern == null || pattern.isBlank()) {
            return clubRepository.findAllLimitedTo(20);
        } else {
            return clubRepository.findByNameContainingLimitedTo(pattern, 20);
        }
    }
}
