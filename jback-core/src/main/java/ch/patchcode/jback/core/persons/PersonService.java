package ch.patchcode.jback.core.persons;

import org.springframework.stereotype.Service;

import java.util.UUID;

public interface PersonService {

    Person getPerson(UUID id);
}
