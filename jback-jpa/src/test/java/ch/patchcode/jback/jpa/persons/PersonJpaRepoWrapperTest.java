package ch.patchcode.jback.jpa.persons;

import ch.patchcode.jback.core.common.Address;
import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.jpa.JpaTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JpaTestConfiguration.class})
class PersonJpaRepoWrapperTest {

    private final PersonJpaRepoWrapper wrapper;

    @Autowired
    public PersonJpaRepoWrapperTest(PersonJpaRepoWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Test
    @Transactional
    void create_and_findById() {

        // arrange
        var draft = new Person.Draft.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                // TODO: address
                // TODO: principal
                // TODO: role
                // TODO: privilege
                .build();

        // act
        var id = wrapper.create(draft).getId();
        var person = wrapper.findById(id);

        // assert
        assertTrue(person.isPresent());
        assertEquals(draft.getFirstName(), person.get().getFirstName());
        assertEquals(draft.getLastName(), person.get().getLastName());
    }
}