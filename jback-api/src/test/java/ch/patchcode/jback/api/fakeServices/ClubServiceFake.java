package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.core.clubs.Club;
import ch.patchcode.jback.core.clubs.ClubService;
import ch.patchcode.jback.security.secBaseImpl.VerificationMean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClubServiceFake implements ClubService<VerificationMean> {

    private final Map<UUID, Club<VerificationMean>> clubs = new HashMap<>();

    public void putClub(Club<VerificationMean> club) {

        clubs.put(club.getId(), club);
    }

    @Override
    public Optional<Club<VerificationMean>> getClub(UUID id) {
        return Optional.ofNullable(clubs.get(id));
    }
}
