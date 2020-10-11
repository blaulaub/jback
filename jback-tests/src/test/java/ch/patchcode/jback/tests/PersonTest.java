package ch.patchcode.jback.tests;

import ch.patchcode.jback.api.persons.Person;
import ch.patchcode.jback.testsInfra.Api;
import ch.patchcode.jback.testsInfra.ApiTestConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static ch.patchcode.jback.testsInfra.Some.personDraft;
import static org.hamcrest.Matchers.contains;

@ApiTestConfiguration.Apply
public class PersonTest {

    private final Api api;

    @Autowired
    public PersonTest(@LocalServerPort int port, ObjectMapper mapper) {
        this.api = new Api(port, mapper);
    }

    @Test
    @DisplayName("posting me during registration works")
    void postingMeDuringRegistrationWorks() throws Exception {

        // arrange
        Person.Draft personDraft = personDraft();

        // act
        var result = api.workflows.postMeToPersons(personDraft).andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.firstName").isEqualTo(personDraft.getFirstName())
                .jsonPath("$.lastName").isEqualTo(personDraft.getLastName())
                .jsonPath("$.address").value(contains(personDraft.getAddress().toArray()));
    }

    @Test
    @DisplayName("posting me twice is forbidden")
    void postingMeTwiceIsForbidden() throws Exception {

        // arrange
        api.workflows.postMeToPersons(personDraft()).andAssumeGoodAndReturn();

        // act
        var result = api.postPersonMe(personDraft()).andReturn();

        // assert
        result.expectStatus().isForbidden();
    }

}
