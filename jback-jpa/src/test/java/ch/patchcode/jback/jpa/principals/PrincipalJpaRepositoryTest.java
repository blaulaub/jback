package ch.patchcode.jback.jpa.principals;

import ch.patchcode.jback.jpa.JpaTestConfiguration;
import ch.patchcode.jback.jpa.persons.PersonJpa;
import ch.patchcode.jback.jpa.persons.PersonJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JpaTestConfiguration.class})
class PrincipalJpaRepositoryTest {

    private final PrincipalJpaRepository principalRepository;

    // not under test, but we also need these for related entities
    private final PersonJpaRepository personRepository;

    @Autowired
    public PrincipalJpaRepositoryTest(
            PrincipalJpaRepository principalRepository,
            PersonJpaRepository personRepository
    ) {
        this.principalRepository = principalRepository;
        this.personRepository = personRepository;
    }

    @Test
    @Transactional
    void save_and_findById() {

        // arrange
        List<PersonJpa> persons = personRepository.saveAll(singletonList(personOf("Huckleberry", "Finn")));
        List<String> authorities = singletonList("CAN_HELP_JIM");

        // act
        var id = principalRepository.save(principalOf(persons, authorities)).getId();
        var principal = principalRepository.findById(id);

        // assert
        assertTrue(principal.isPresent());
        assertIterableEquals(principal.get().getPersons(), persons);
        assertIterableEquals(principal.get().getAuthorities(), authorities);
    }

    private PrincipalJpa principalOf(
            List<PersonJpa> persons,
            List<String> authorities
    ) {
        var principal = new PrincipalJpa();
        principal.setPersons(persons);
        principal.setAuthorities(authorities);
        return principal;
    }

    private PersonJpa personOf(String firstName, String lastName) {
        var person = new PersonJpa();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }
}
