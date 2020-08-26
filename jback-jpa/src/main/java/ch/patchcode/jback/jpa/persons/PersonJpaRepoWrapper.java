package ch.patchcode.jback.jpa.persons;

import ch.patchcode.jback.core.common.Address;
import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.core.persons.PersonRepository;
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
    public Optional<Person> findOne(UUID id) {

        return personJpaRepository.findById(id).map(PersonJpaRepoWrapper::toPerson);
    }

    public static Person toPerson(ch.patchcode.jback.jpa.persons.Person it) {
        return new Person.Builder()
                .setId(it.getId())
                .setFirstName(it.getFirstName())
                .setLastName(it.getLastName())
                .setAddress(new Address.Builder().setLines(new String[]{
                        it.getAddress1(),
                        it.getAddress2(),
                        it.getAddress3(),
                        it.getAddress4(),
                        it.getAddress5()
                }).build()).build();
    }
}
