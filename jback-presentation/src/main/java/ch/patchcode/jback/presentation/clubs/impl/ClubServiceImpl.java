package ch.patchcode.jback.presentation.clubs.impl;

import ch.patchcode.jback.presentation.clubs.Club;
import ch.patchcode.jback.presentation.clubs.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service("presentation.clubService")
public class ClubServiceImpl implements ClubService {

    private final ch.patchcode.jback.core.clubs.ClubService clubService;

    @Autowired
    public ClubServiceImpl(ch.patchcode.jback.core.clubs.ClubService clubService) {

        this.clubService = clubService;
    }

    @Override
    public Optional<Club> getClub(UUID id) {

        return clubService.getClub(id).map(Club::fromDomain);
    }
}
