package ch.patchcode.jback.coreEntities.roles;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {

    Optional<Role> findById(UUID id);

    Role create(Role.Draft draft);
}
