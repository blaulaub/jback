package ch.patchcode.jback.core.entities;

import java.util.Optional;
import java.util.UUID;

public interface ClubRepository {

    Optional<Club> findById(UUID id);

    Club create(Club.Draft draft);
}
