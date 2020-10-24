package ch.patchcode.jback.tests;

import ch.patchcode.jback.api.persons.PersonWithPasswordDraft;
import ch.patchcode.jback.testsInfra.Api;
import ch.patchcode.jback.testsInfra.ApiTestConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;

import static ch.patchcode.jback.testsInfra.Some.personWithPasswordDraft;
import static org.hamcrest.Matchers.contains;

@ApiTestConfiguration.Apply
public class PersonTest {

    private final Api api;

    @Autowired
    public PersonTest(@LocalServerPort int port, ObjectMapper mapper, Environment env) {
        this.api = new Api(port, mapper, env);
    }

    @Test
    @DisplayName("posting me during registration works")
    void postingMeDuringRegistrationWorks() throws Exception {

        // arrange
        PersonWithPasswordDraft meDraft = personWithPasswordDraft();

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
        api.workflows.registerAndPostMeToPersons(personWithPasswordDraft()).andAssumeGoodAndReturn();

        // act
        var result = api.postPersonWithPassword(personWithPasswordDraft()).andReturn();

        // assert
        result.expectStatus().isForbidden();
    }

}
