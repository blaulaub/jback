package ch.patchcode.jback.jpa.entities;

import ch.patchcode.jback.coreEntities.Person;

import java.util.Optional;
import java.util.UUID;

public interface PersonJpaRepository {

    <S extends PersonJpa> S save(S s);

    Optional<PersonJpa> findById(UUID uuid);

    default PersonJpa toJpaIfConsistent(Person person) {

        var personJpa = this.findById(person.getId());
        if (personJpa.isEmpty()) {
            throw new RuntimeException("person not found");
        }
        if (personJpa.map(PersonJpa::toDomain)
                .filter(it -> it.equals(person))
                .isEmpty()) {
            throw new RuntimeException("person mismatch (outdated?)");
        }
        return personJpa.get();
    }
}
