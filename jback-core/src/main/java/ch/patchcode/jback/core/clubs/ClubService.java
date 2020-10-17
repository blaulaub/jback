package ch.patchcode.jback.core.clubs;

import ch.patchcode.jback.coreEntities.Club;

import java.util.Optional;
import java.util.UUID;

public interface ClubService {

    Optional<Club> getClub(UUID id);

    Club create(Club.Draft draft);
}
