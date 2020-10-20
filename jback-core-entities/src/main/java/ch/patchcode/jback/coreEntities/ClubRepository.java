package ch.patchcode.jback.coreEntities;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClubRepository {

    Optional<Club> findById(UUID id);

    Club create(Club.Draft draft);

    List<Club> findAllLimitedTo(int count);

    List<Club> findByNameContainingLimitedTo(String pattern, int count);
}
