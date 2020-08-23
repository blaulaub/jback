package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.core.clubs.Club;
import ch.patchcode.jback.core.clubs.ClubService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ClubServiceFake implements ClubService {

    private Map<UUID, Club> clubs = new HashMap<>();

    public void putClub(Club club) {

        clubs.put(club.getId(), club);
    }

    @Override
    public Club getClub(UUID id) {
        return clubs.get(id);
    }
}
