package ch.patchcode.jback.core.persons;

import java.util.UUID;

public interface PersonRepository {

    Person findOne(UUID id);
}
