package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.core.persons.PersonService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PersonServiceFake implements PersonService {

    private Map<UUID, Person> persons = new HashMap<>();

    public void putPerson(Person person) {

        persons.put(person.getId(), person);
    }

    @Override
    public Person getPerson(UUID id) {
        return persons.get(id);
    }
}
