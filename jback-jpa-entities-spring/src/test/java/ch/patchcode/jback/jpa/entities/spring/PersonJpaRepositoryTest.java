package ch.patchcode.jback.jpa.entities.spring;

import ch.patchcode.jback.jpa.entities.PersonJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { JpaTestConfiguration.class })
class PersonJpaRepositoryTest {

    private final PersonJpaRepository personJpaRepository;

    @Autowired
    public PersonJpaRepositoryTest(PersonJpaRepository personJpaRepository) {

        this.personJpaRepository = personJpaRepository;
    }

    @Test
    @Transactional
    public void save_and_findById() {

        // arrange
        var input = new PersonJpa();
        input.setFirstName("Tom");
        input.setLastName("Sawyer");
        input.setAddressLines(asList(
                PersonJpa.AddressLine.of(1, "Technoparkstrasse 1"),
                PersonJpa.AddressLine.of(2,"8051 Zürich"),
                PersonJpa.AddressLine.of(3,"Switzerland")
        ));

        // act
        var id = personJpaRepository.save(input).getId();
        var output = personJpaRepository.findById(id);

        // assert
        assertTrue(output.isPresent());
        assertEquals("Tom", output.get().getFirstName());
        assertEquals("Sawyer", output.get().getLastName());
        assertIterableEquals(
                asList("Technoparkstrasse 1", "8051 Zürich", "Switzerland"),
                output.get().getAddressLines().stream().map(PersonJpa.AddressLine::getValue).collect(toList())
        );
    }
}
