package ch.patchcode.jback.jpa.wrappers;

import ch.patchcode.jback.coreEntities.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JpaWrapperTestConfiguration.class})
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