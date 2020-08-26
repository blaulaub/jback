package ch.patchcode.jback.core.clubs;

import java.util.Optional;
import java.util.UUID;

public interface ClubService {

    Optional<Club> getClub(UUID id);
}
