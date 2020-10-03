package ch.patchcode.jback.jpa.wrappers;

import ch.patchcode.jback.coreEntities.Club;
import ch.patchcode.jback.coreEntities.Address;
import ch.patchcode.jback.coreEntities.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JpaWrapperTestConfiguration.class})
class ClubJpaRepoWrapperTest {

    private final PersonJpaRepoWrapper personRepoWrapper;

    private final ClubJpaRepoWrapper wrapper;

    private Person contact;

    @Autowired
    public ClubJpaRepoWrapperTest(
            PersonJpaRepoWrapper personRepoWrapper,
            ClubJpaRepoWrapper wrapper) {

        this.personRepoWrapper = personRepoWrapper;
        this.wrapper = wrapper;
    }

    @BeforeEach
    void setUp() {

        var personDraft = new Person.Draft.Builder()
                .setFirstName("Ernst")
                .setLastName("Graf")
                .setAddress(new Address.Builder()
                        .addLines("Berner Sport ch.patchcode.jback.coreEntities.Club Young Boys", "Postfach 61", "3000 Bern 22")
                        .build())
                .buildPartial();
        contact = personRepoWrapper.create(personDraft);
    }

    @Test
    @Transactional
    void create_and_findById() {

        // arrange
        var draft = new Club.Draft.Builder()
                .setName("Young Boys")
                .setContact(contact)
                .setUrl(URI.create("https://www.bscyb.ch/"))
                .build();

        // act
        var id = wrapper.create(draft).getId();
        var club = wrapper.findById(id);

        // assert
        assertTrue(club.isPresent());
        assertAll(
                () -> assertEquals(id, club.get().getId()),
                () -> assertEquals(draft.getName(), club.get().getName()),
                () -> assertEquals(draft.getContact(), club.get().getContact()),
                () -> assertEquals(draft.getUrl(), club.get().getUrl())
        );
    }
}