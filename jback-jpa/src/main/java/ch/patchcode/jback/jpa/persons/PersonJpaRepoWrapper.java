package ch.patchcode.jback.jpa.persons;

import ch.patchcode.jback.core.common.Address;
import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.core.persons.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

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
        draft.getAddress()
                .map(Address::getLines)
                .map(lines -> IntStream.range(0, lines.size())
                        .mapToObj(idx -> PersonJpa.AddressLine.of(idx, lines.get(idx)))
                        .collect(toList()))
                .ifPresent(person::setAddressLines);
        return personJpaRepository.save(person).toDomain();
    }
}
