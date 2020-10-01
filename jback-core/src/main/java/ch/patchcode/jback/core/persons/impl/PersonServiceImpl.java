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

    private final AuthorizationManager<Person, TVerificationMean, TPrincipal> authorizationManager;
    private final PersonRepository personRepository;

    @Inject
    public PersonServiceImpl(
            AuthorizationManager<Person, TVerificationMean, TPrincipal> authorizationManager,
            PersonRepository personRepository) {

        this.authorizationManager = authorizationManager;
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
    public Person createClient(
            Person.Draft draft,
            TPrincipal principal
    ) {

        var person = personRepository.create(draft);
        authorizationManager.addClient(principal, person);
        return person;
    }
}
