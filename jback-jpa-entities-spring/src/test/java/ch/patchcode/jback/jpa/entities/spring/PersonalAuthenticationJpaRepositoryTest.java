package ch.patchcode.jback.jpa.entities.spring;

import ch.patchcode.jback.jpa.entities.PersonJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JpaTestConfiguration.class})
class PersonalAuthenticationJpaRepositoryTest {

    private final PersonalAuthenticationJpaRepository principalRepository;

    // not under test, but we also need these for related entities
    private final PersonJpaRepository personRepository;

    @Autowired
    public PersonalAuthenticationJpaRepositoryTest(
            PersonalAuthenticationJpaRepository principalRepository,
            PersonJpaRepository personRepository
    ) {
        this.principalRepository = principalRepository;
        this.personRepository = personRepository;
    }

    @Test
    @Transactional
    void save_and_findById() {

        // arrange
        List<PersonJpa> persons = personRepository.saveAll(singletonList(SomeData.personOf("Huckleberry", "Finn")));
        List<String> authorities = singletonList("CAN_HELP_JIM");

        // act
        var id = principalRepository.save(SomeData.principalOf(persons, authorities)).getId();
        var principal = principalRepository.findById(id);

        // assert
        assertTrue(principal.isPresent());
        assertIterableEquals(principal.get().getClients(), persons);
        assertIterableEquals(principal.get().getAuthorities(), authorities);
    }
}
