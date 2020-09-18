package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.core.persons.PersonService;
import ch.patchcode.jback.security.Authentication;
import ch.patchcode.jback.security.secBaseImpl.VerificationMean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonServiceFake implements PersonService<VerificationMean, Authentication> {

    private final Map<UUID, Person<VerificationMean>> persons = new HashMap<>();

    public void putPerson(Person<VerificationMean> person) {

        persons.put(person.getId(), person);
    }

    @Override
    public Optional<Person<VerificationMean>> getPerson(UUID id) {

        return Optional.ofNullable(persons.get(id));
    }

    @Override
    public Person<VerificationMean> create(Person.Draft<VerificationMean> draft) {

        return createBuilderFrom(draft).build();
    }

    @Override
    public Person<VerificationMean> createClient(Person.Draft<VerificationMean> draft, Authentication principal) {

        return createBuilderFrom(draft)
                .addPrincipals(principal)
                .build();
    }

    private Person.Builder<VerificationMean> createBuilderFrom(Person.Draft<VerificationMean> draft) {

        var builder = new Person.Builder<VerificationMean>();
        builder.setId(UUID.randomUUID());
        builder.setFirstName(draft.getFirstName());
        builder.setLastName(draft.getLastName());
        draft.getAddress().ifPresent(builder::setAddress);
        return builder;
    }
}
