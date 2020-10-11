package ch.patchcode.jback.tests;

import ch.patchcode.jback.api.persons.Person;
import ch.patchcode.jback.testsInfra.Api;
import ch.patchcode.jback.testsInfra.ApiTestConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static ch.patchcode.jback.testsInfra.Some.meDraft;
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
        Person.MeDraft meDraft = meDraft();

        // act
        var result = api.workflows.registerAndPostMeToPersons(meDraft).andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.firstName").isEqualTo(meDraft.getFirstName())
                .jsonPath("$.lastName").isEqualTo(meDraft.getLastName())
                .jsonPath("$.address").value(contains(meDraft.getAddress().toArray()));
    }

    @Test
    @DisplayName("posting me twice is forbidden")
    void postingMeTwiceIsForbidden() throws Exception {

        // arrange
        api.workflows.registerAndPostMeToPersons(meDraft()).andAssumeGoodAndReturn();

        // act
        var result = api.postPersonMe(meDraft()).andReturn();

        // assert
        result.expectStatus().isForbidden();
    }

}
