package ch.patchcode.jback.core.persons;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository {

    Optional<Person> findOne(UUID id);
}
