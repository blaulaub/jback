package ch.patchcode.jback.jpa.wrappers;

import ch.patchcode.jback.core.entities.Person;
import ch.patchcode.jback.core.entities.PersonRepository;
import ch.patchcode.jback.jpa.entities.PersonJpa;
import ch.patchcode.jback.jpa.entities.PersonJpaRepository;
import ch.patchcode.jback.security.entities.VerificationMean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PersonJpaRepoWrapper implements PersonRepository<VerificationMean> {

    private final PersonJpaRepository personJpaRepository;

    @Autowired
    public PersonJpaRepoWrapper(PersonJpaRepository personJpaRepository) {
        this.personJpaRepository = personJpaRepository;
    }

    @Override
    public Optional<Person<VerificationMean>> findById(UUID id) {

        return personJpaRepository.findById(id).map(PersonJpa::toDomain);
    }

    @Override
    public Person<VerificationMean> create(Person.Draft<VerificationMean> draft) {

        PersonJpa person = PersonJpa.fromDomain(draft);
        return personJpaRepository.save(person).toDomain();
    }
}
