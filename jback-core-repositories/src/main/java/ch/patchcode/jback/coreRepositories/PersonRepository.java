package ch.patchcode.jback.coreRepositories;

import ch.patchcode.jback.coreEntities.Person;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository {

    Optional<Person> findById(UUID id);

    Person create(Person.Draft draft);
}
