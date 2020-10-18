package ch.patchcode.jback.coreEntities;

import java.util.Optional;
import java.util.UUID;

public interface DojoRepository {

    Optional<Dojo> findById(UUID id);

    Dojo create(Dojo.Draft draft);
}
