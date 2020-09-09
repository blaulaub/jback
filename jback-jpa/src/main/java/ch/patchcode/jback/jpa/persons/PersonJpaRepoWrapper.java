package ch.patchcode.jback.jpa.persons;

import ch.patchcode.jback.core.common.Address;
import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.core.persons.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class PersonJpaRepoWrapper implements PersonRepository {

    private final PersonJpaRepository personJpaRepository;

    @Autowired
    public PersonJpaRepoWrapper(PersonJpaRepository personJpaRepository) {
        this.personJpaRepository = personJpaRepository;
    }

    @Override
    public Optional<ch.patchcode.jback.core.persons.Person> findById(UUID id) {

        return personJpaRepository.findById(id).map(PersonJpa::toDomain);
    }

    @Override
    public Person create(Person.Draft draft) {
        var person = new PersonJpa();
        person.setFirstName(draft.getFirstName());
        person.setLastName(draft.getLastName());
        draft.getAddress().map(Address::getLines).ifPresent(lines -> {
            ifPresentThenTransfer(lines, 0, person::setAddress1);
            ifPresentThenTransfer(lines, 1, person::setAddress2);
            ifPresentThenTransfer(lines, 2, person::setAddress3);
            ifPresentThenTransfer(lines, 3, person::setAddress4);
            ifPresentThenTransfer(lines, 4, person::setAddress5);
        });
        return personJpaRepository.save(person).toDomain();
    }

    private static void ifPresentThenTransfer(String[] lines, int idx, Consumer<String> consumer) {
        if (lines.length > idx) {
            consumer.accept(lines[idx]);
        }
    }
}
