package ch.patchcode.jback.jpa.persons;

import ch.patchcode.jback.jpa.JpaTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { JpaTestConfiguration.class })
class PersonJpaRepositoryTest {

    private final PersonJpaRepository personRepository;

    @Autowired
    public PersonJpaRepositoryTest(PersonJpaRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Test
    public void test() {

        // arrange
        var input = new Person();
        input.setFirstName("Tom");
        input.setLastName("Sawyer");
        input.setAddress1("Technoparkstrasse 1");
        input.setAddress2("8051 Zürich");
        input.setAddress5("Switzerland");

        // act
        var id = personRepository.save(input).getId();
        var output = personRepository.findById(id);

        // assert
        assertTrue(output.isPresent());
        assertEquals("Tom", output.get().getFirstName());
        assertEquals("Sawyer", output.get().getLastName());
        assertEquals("Technoparkstrasse 1", output.get().getAddress1());
        assertEquals("8051 Zürich", output.get().getAddress2());
        assertEquals("Switzerland", output.get().getAddress5());
    }
}