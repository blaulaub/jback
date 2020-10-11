package ch.patchcode.jback.tests;

import ch.patchcode.jback.presentation.Perspective;
import ch.patchcode.jback.testsInfra.Api;
import ch.patchcode.jback.testsInfra.ApiTestConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static ch.patchcode.jback.testsInfra.ConstantVerificationCodeProvider.VERIFICATION_CODE;
import static ch.patchcode.jback.testsInfra.Some.initialRegistrationData;
import static ch.patchcode.jback.testsInfra.Some.personDraft;
import static org.hamcrest.Matchers.equalTo;

@ApiTestConfiguration.Apply
class SessionTest {

    private final Api api;

    @Autowired
    public SessionTest(@LocalServerPort int port, ObjectMapper mapper) {
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

    @Test
    @DisplayName("after putting wrong verification perspective remains GUEST")
    void afterPuttingWrongVerificationSessionRemainsGuest() throws Exception {

        // arrange
        final String wrongCode = VERIFICATION_CODE + 1;
        api.workflows.registerWithCode(initialRegistrationData(), wrongCode);

        // act
        var result = api.getSession().andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.perspective").value(equalTo(Perspective.GUEST.toString()));
    }

    @Test
    @DisplayName("after putting correct verification perspective becomes ENROLLING")
    void afterPuttingCorrectVerificationSessionBecomesEnrolling() throws Exception {

        // arrange
        api.workflows.registerWithCode(initialRegistrationData(), VERIFICATION_CODE).andAssumeGoodAndReturn();

        // act
        var result = api.getSession().andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.perspective").value(equalTo(Perspective.ENROLLING.toString()));
    }

    @Test
    @DisplayName("after logout from ENROLLING perspective becomes GUEST")
    void afterLogoutFromEnrollingPerspectiveBecomesGuest() throws Exception {

        // arrange
        api.workflows.registerWithCode(initialRegistrationData(), VERIFICATION_CODE).andAssumeGoodAndReturn();
        api.postLogout().andAssumeGoodAndReturn();

        // act
        var result = api.getSession().andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.perspective").value(equalTo(Perspective.GUEST.toString()));
    }

    @Test
    @DisplayName("after posting me perspective becomes MEMBER")
    void afterPostingMePerspectiveBecomesMember() throws Exception {

        // arrange
        api.workflows.registerAndPostMeToPersons(personDraft()).andAssumeGoodAndReturn();

        // act
        var result = api.getSession().andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.perspective").value(equalTo(Perspective.MEMBER.toString()));
    }
}
