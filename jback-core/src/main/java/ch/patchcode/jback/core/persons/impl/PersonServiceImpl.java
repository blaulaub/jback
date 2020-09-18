package ch.patchcode.jback.core.persons.impl;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.core.persons.PersonRepository;
import ch.patchcode.jback.core.persons.PersonService;
import ch.patchcode.jback.secBase.AuthorizationManager;
import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.secBase.secModelImpl.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PersonServiceImpl<TVerificationMean extends VerificationMean> implements PersonService<TVerificationMean> {

    private final AuthorizationManager<Person<TVerificationMean>, ?, TVerificationMean> authorizationManager;
    private final PersonRepository<TVerificationMean> personRepository;

    @Autowired
    public PersonServiceImpl(
            AuthorizationManager<Person<TVerificationMean>, ?, TVerificationMean> authorizationManager,
            PersonRepository<TVerificationMean> personRepository) {

        this.authorizationManager = authorizationManager;
        this.personRepository = personRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Person<TVerificationMean>> getPerson(UUID id) {
        return personRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Person<TVerificationMean> create(Person.Draft<TVerificationMean> draft) {

        return personRepository.create(draft);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Person<TVerificationMean> createClient(
            Person.Draft<TVerificationMean> draft,
            Principal<TVerificationMean> principal
    ) {

        var person = personRepository.create(draft);
        authorizationManager.addClient(principal, person);
        return person;
    }
}
