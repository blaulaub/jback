package ch.patchcode.jback.tests;

import ch.patchcode.jback.api.clubs.Club;
import ch.patchcode.jback.testsInfra.Api;
import ch.patchcode.jback.testsInfra.ApiTestConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

@ApiTestConfiguration.Apply
@TestPropertySource(properties = {
        "ADMIN_USERNAME=admin",
        "ADMIN_PASSWORD=secret"
})
public class ClubTest {

    private final Api api;

    @Autowired
    public ClubTest(@LocalServerPort int port, ObjectMapper mapper, Environment env) {
        this.api = new Api(port, mapper, env);
    }

    @Test
    @DisplayName("superuser can create new club")
    void superUserCanCreateNewClub() throws Exception {

        // arrange
        api.workflows.loginAsSuperuser().andAssumeGoodAndReturn();

        Club.Draft draft = new Club.Draft.Builder()
                .setName("Screaming Seagulls")
                .build();

        // act
        var result = api.postClub(draft).andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.name").isEqualTo(draft.getName());
    }
}
