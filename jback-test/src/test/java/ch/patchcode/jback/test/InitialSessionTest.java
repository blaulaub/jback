package ch.patchcode.jback.test;

import ch.patchcode.jback.presentation.Perspective;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    public InitialSessionTest(Api api) {
        this.api = api;
    }

    @Test
    @DisplayName("the initial session perspective is 'GUEST'")
    void initialPerspectiveIsGuest() throws Exception {

        // act
        var result = api.getSession().andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.perspective").value(equalTo(Perspective.GUEST.toString()));
    }
}
