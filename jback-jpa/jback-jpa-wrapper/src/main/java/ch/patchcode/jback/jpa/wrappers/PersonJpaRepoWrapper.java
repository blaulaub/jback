package ch.patchcode.jback.jpa.wrappers;

import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.coreEntities.PersonRepository;
import ch.patchcode.jback.jpa.entities.PersonJpa;
import ch.patchcode.jback.jpa.entities.PersonJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PersonJpaRepoWrapper implements PersonRepository {

    private final PersonJpaRepository personJpaRepository;

    @Autowired
    public PersonJpaRepoWrapper(PersonJpaRepository personJpaRepository) {
        this.personJpaRepository = personJpaRepository;
    }

    @Override
    public Optional<Person> findById(UUID id) {

        return personJpaRepository.findById(id).map(PersonJpa::toDomain);
    }

    @Override
    public Person create(Person.Draft draft) {

        PersonJpa person = PersonJpa.fromDomain(draft);
        return personJpaRepository.save(person).toDomain();
    }
}
