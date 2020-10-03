package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.core.persons.PersonService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonServiceFake implements PersonService {

    private final Map<UUID, Person> persons = new HashMap<>();

    public void putPerson(Person person) {

        persons.put(person.getId(), person);
    }

    @Override
    public Optional<Person> getPerson(UUID id) {

        return Optional.ofNullable(persons.get(id));
    }

    @Override
    public Person create(Person.Draft draft) {

        return createBuilderFrom(draft).build();
    }

    private Person.Builder createBuilderFrom(Person.Draft draft) {

        var builder = new Person.Builder();
        builder.setId(UUID.randomUUID());
        builder.setFirstName(draft.getFirstName());
        builder.setLastName(draft.getLastName());
        draft.getAddress().ifPresent(builder::setAddress);
        return builder;
    }
}
