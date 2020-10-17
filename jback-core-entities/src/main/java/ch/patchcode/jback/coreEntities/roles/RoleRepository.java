package ch.patchcode.jback.coreEntities.roles;

import ch.patchcode.jback.coreEntities.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {

    Optional<Role> findById(UUID id);

    Role create(Role.Draft draft);

    List<Role> findByPersonIn(List<Person> persons);
}
