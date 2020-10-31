package ch.patchcode.jback.jpa.entitiesSpring;

import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.jpa.entities.PersonJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonJpaRepository extends JpaRepository<PersonJpa, UUID> {

    @Override
    <S extends PersonJpa> S save(S s);

    @Override
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
