package ch.patchcode.jback.core.clubs;

import ch.patchcode.jback.coreEntities.Club;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClubService {

    Optional<Club> getClub(UUID id);

    Club create(Club.Draft draft);

    List<Club> find(String pattern);
}
