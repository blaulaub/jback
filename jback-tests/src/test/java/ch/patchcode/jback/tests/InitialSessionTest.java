package ch.patchcode.jback.tests;

import ch.patchcode.jback.presentation.Perspective;
import ch.patchcode.jback.testsInfra.Api;
import ch.patchcode.jback.testsInfra.ApiTestConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static org.hamcrest.Matchers.equalTo;

/**
 * Tests (or requirements) on a fresh session.
 * <p>
 * Fresh means the current user is not authenticated in any way.
 */
@ApiTestConfiguration.Apply
class InitialSessionTest {

    private final Api api;

    @Autowired
    public InitialSessionTest(@LocalServerPort int port, ObjectMapper mapper) {
        this.api = new Api(port, mapper);
    }

    @Test
    @DisplayName("the initial session perspective is GUEST")
    void initialPerspectiveIsGuest() {

        // act
        var result = api.getSession().andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.perspective").value(equalTo(Perspective.GUEST.toString()));
    }
}
