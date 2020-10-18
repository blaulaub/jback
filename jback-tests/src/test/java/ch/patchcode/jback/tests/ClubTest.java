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

import static ch.patchcode.jback.testsInfra.Some.minimalisticClubDraft;

@ApiTestConfiguration.Apply
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

        Club.Draft draft = minimalisticClubDraft();

        // act
        var result = api.postClub(draft).andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.name").isEqualTo(draft.getName());
    }

    @Test
    @DisplayName("superuser can get club after creating it")
    void superUserCanGetClubAfterCreatingIt() throws Exception {

        // arrange
        api.workflows.loginAsSuperuser().andAssumeGoodAndReturn();
        Club.Draft draft = minimalisticClubDraft();
        var clubId = api.postClub(draft).andAssumeGoodAndReturn(Club.class).getId();

        // act
        var result = api.getClub(clubId).andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(clubId.toString())
                .jsonPath("$.name").isEqualTo(draft.getName());
    }
}
