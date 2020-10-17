package ch.patchcode.jback.tests;

import ch.patchcode.jback.api.session.LoginData;
import ch.patchcode.jback.api.session.LoginResponse;
import ch.patchcode.jback.api.verification.VerificationByUsernameAndPassword;
import ch.patchcode.jback.presentation.Perspective;
import ch.patchcode.jback.testsInfra.Api;
import ch.patchcode.jback.testsInfra.ApiTestConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

import static ch.patchcode.jback.testsInfra.ConstantVerificationCodeProvider.VERIFICATION_CODE;
import static ch.patchcode.jback.testsInfra.Some.initialRegistrationData;
import static ch.patchcode.jback.testsInfra.Some.meDraft;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@ApiTestConfiguration.Apply
@TestPropertySource(properties = {
        "ADMIN_USERNAME=admin",
        "ADMIN_PASSWORD=secret"
})
class SessionTest {

    @Autowired
    private Environment env;

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
        api.workflows.registerAndPostMeToPersons(meDraft()).andAssumeGoodAndReturn();

        // act
        var result = api.getSession().andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.perspective").value(equalTo(Perspective.MEMBER.toString()));
    }

    @Test
    @DisplayName("can login with username and password after registering me")
    void canLoginWithUsernameAndPasswordAfterRegisteringMe() throws Exception {

        // arrange
        var registrationData = meDraft();
        api.workflows.registerAndPostMeToPersons(registrationData).andAssumeGoodAndReturn();
        api.postLogout();
        var loginData = new LoginData.Builder()
                .setUserIdentification(registrationData.getUsername())
                .setVerificationMean(VerificationByUsernameAndPassword.Draft.create(
                        registrationData.getUsername(),
                        registrationData.getPassword()))
                .build();

        // act
        var result = api.postLogin(loginData).andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.kind").value(equalTo(LoginResponse.Kind.SUCCESS.toString()));
    }

    @Test
    @DisplayName("can login with superuser credentials")
    void canLoginWithSuperuserCredentials() throws Exception {

        // arrange
        String username = env.getProperty("ADMIN_USERNAME");
        String password = env.getProperty("ADMIN_PASSWORD");
        assumeTrue("admin".equals(username));
        assumeTrue("secret".equals(password));

        var loginData = new LoginData.Builder()
                .setUserIdentification(username)
                .setVerificationMean(VerificationByUsernameAndPassword.Draft.create(username, password))
                .build();

        // act
        var result = api.postLogin(loginData).andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.kind").value(equalTo(LoginResponse.Kind.SUCCESS.toString()));
    }
}
