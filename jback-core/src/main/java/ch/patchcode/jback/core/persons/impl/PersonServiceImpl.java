package ch.patchcode.jback.core.persons.impl;

import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.coreRepositories.PersonRepository;
import ch.patchcode.jback.core.persons.PersonService;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Inject
    public PersonServiceImpl(PersonRepository personRepository) {

        this.personRepository = personRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Person> getPerson(UUID id) {
        return personRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Person create(Person.Draft draft) {

        return personRepository.create(draft);
    }
}
