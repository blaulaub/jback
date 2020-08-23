package ch.patchcode.jback.core.clubs;

import java.util.UUID;

public interface ClubRepository {

    Club findOne(UUID id);
}
