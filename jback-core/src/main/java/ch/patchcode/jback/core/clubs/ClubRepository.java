package ch.patchcode.jback.core.clubs;

import java.util.Optional;
import java.util.UUID;

public interface ClubRepository {

    Optional<Club> findOne(UUID id);
}
