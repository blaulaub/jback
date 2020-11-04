package ch.patchcode.jback.coreRepositories;

import ch.patchcode.jback.coreEntities.Dojo;

import java.util.Optional;
import java.util.UUID;

public interface DojoRepository {

    Optional<Dojo> findById(UUID id);

    Dojo create(Dojo.Draft draft);
}
