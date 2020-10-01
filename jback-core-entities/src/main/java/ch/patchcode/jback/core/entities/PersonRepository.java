package ch.patchcode.jback.core.entities;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository {

    Optional<Person> findById(UUID id);

    Person create(Person.Draft draft);
}
