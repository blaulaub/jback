package ch.patchcode.jback.core.persons;

import ch.patchcode.jback.core.entities.Person;

import java.util.Optional;
import java.util.UUID;

public interface PersonService {

    Optional<Person> getPerson(UUID id);

    /**
     * Creates a new {@link Person}.
     */
    Person create(Person.Draft draft);
}
