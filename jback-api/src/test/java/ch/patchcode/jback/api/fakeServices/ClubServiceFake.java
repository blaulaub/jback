package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.presentation.clubs.Club;
import ch.patchcode.jback.presentation.clubs.ClubService;
import ch.patchcode.jback.security.secBaseImpl.VerificationMean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service("presentation.clubService")
public class ClubServiceFake implements ClubService {

    private final Map<UUID, Club> clubs = new HashMap<>();

    public void putClub(Club club) {

        clubs.put(club.getId(), club);
    }

    @Override
    public Optional<Club> getClub(UUID id) {
        return Optional.ofNullable(clubs.get(id));
    }
}
