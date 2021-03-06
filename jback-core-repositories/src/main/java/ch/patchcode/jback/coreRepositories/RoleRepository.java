package ch.patchcode.jback.coreRepositories;

import ch.patchcode.jback.coreEntities.Club;
import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.coreEntities.roles.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {

    Optional<Role> findById(UUID id);

    Role create(Role.Draft draft);

    List<Role> findByPersonAndClub(Person person, Club club);

    List<Role> findByPersonIn(List<Person> persons);
}
