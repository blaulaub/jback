package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.core.clubs.Club;
import ch.patchcode.jback.core.clubs.ClubService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
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
