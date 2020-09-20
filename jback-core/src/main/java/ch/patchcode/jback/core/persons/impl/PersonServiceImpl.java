package ch.patchcode.jback.core.persons.impl;

import ch.patchcode.jback.core.entities.Person;
import ch.patchcode.jback.core.entities.PersonRepository;
import ch.patchcode.jback.core.persons.PersonService;
import ch.patchcode.jback.secBase.AuthorizationManager;
import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.secBase.secModelImpl.Principal;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

public class PersonServiceImpl<
        TVerificationMean extends VerificationMean,
        TPrincipal extends Principal<TVerificationMean>
        > implements PersonService<TVerificationMean, TPrincipal> {

    private final AuthorizationManager<Person<TVerificationMean>, TVerificationMean, TPrincipal> authorizationManager;
    private final PersonRepository<TVerificationMean> personRepository;

    @Inject
    public PersonServiceImpl(
            AuthorizationManager<Person<TVerificationMean>, TVerificationMean, TPrincipal> authorizationManager,
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
            TPrincipal principal
    ) {

        var person = personRepository.create(draft);
        authorizationManager.addClient(principal, person);
        return person;
    }
}
