package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.core.entities.Person;
import ch.patchcode.jback.core.persons.PersonService;
import ch.patchcode.jback.security.entities.Principal;
import ch.patchcode.jback.security.entities.VerificationMean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonServiceFake implements PersonService<VerificationMean, Principal> {

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

    @Override
    public Person createClient(Person.Draft draft, Principal principal) {

        return createBuilderFrom(draft)
                .build();
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
