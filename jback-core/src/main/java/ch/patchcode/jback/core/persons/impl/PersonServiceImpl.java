package ch.patchcode.jback.core.persons.impl;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.core.persons.PersonRepository;
import ch.patchcode.jback.core.persons.PersonService;
import ch.patchcode.jback.secBase.secModelImpl.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Person createClient(Person.Draft draft, Principal principal) {

        return personRepository.create(draft);
    }
}
