package ch.patchcode.jback.core.persons;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

public interface PersonService {

    Optional<Person> getPerson(UUID id);
}
