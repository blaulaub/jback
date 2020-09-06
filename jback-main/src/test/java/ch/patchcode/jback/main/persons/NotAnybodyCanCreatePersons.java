package ch.patchcode.jback.main.persons;

import ch.patchcode.jback.api.persons.PersonDraft;
import ch.patchcode.jback.main.MainTestConfiguration;
import ch.patchcode.jback.main.restApi.RestApi;
import ch.patchcode.jback.main.util.RestSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {MainTestConfiguration.class})
public class NotAnybodyCanCreatePersons {

    private final RestApi api;

    @Autowired
    public NotAnybodyCanCreatePersons(@LocalServerPort int port, TestRestTemplate restTemplate, ObjectMapper objectMapper) {
        this.api = new RestApi(new RestSession(port, restTemplate, objectMapper));
    }

    @Test
    void withoutAuthorization_creatingPerson_fails() throws Exception {

        PersonDraft newPerson = new PersonDraft.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .build();

        // TODO should return 401 or 403
        assertThrows(RuntimeException.class, () -> api.personsPostNewPerson(newPerson));
    }
}
