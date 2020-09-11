package ch.patchcode.jback.core.persons;

import java.util.Optional;
import java.util.UUID;

public interface PersonService {

    Optional<Person> getPerson(UUID id);

    Person create(Person.Draft draft);
}
