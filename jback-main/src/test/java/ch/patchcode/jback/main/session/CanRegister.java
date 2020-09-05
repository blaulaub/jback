package ch.patchcode.jback.main.session;

import ch.patchcode.jback.api.registration.PendingRegistrationInfo;
import ch.patchcode.jback.api.registration.VerificationCode;
import ch.patchcode.jback.api.session.SessionInfo;
import ch.patchcode.jback.main.MainTestConfiguration;
import ch.patchcode.jback.main.fakes.FixVerificationCodeProvider;
import ch.patchcode.jback.main.util.RestSession;
import ch.patchcode.jback.main.util.SomeData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {MainTestConfiguration.class})
class CanRegister {

    private final RestSession restSession;

    @Autowired
    public CanRegister(@LocalServerPort int port, TestRestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restSession = new RestSession(port, restTemplate, objectMapper);
    }

    private ResponseEntity<PendingRegistrationInfo> postSomeInitialRegistrationData() throws Exception {

        // arrange
        var initialRegistrationData = SomeData.someInitialRegistrationData();

        // act - request registration
        return restSession.post("/api/v1/registration", initialRegistrationData, PendingRegistrationInfo.class);
    }

    @Test
    void postSomeInitialRegistrationData_works() throws Exception {

        // act
        var result = postSomeInitialRegistrationData();

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    private ResponseEntity<Void> registerWithCode(PendingRegistrationInfo registrationInfo, String verificationCode)
            throws Exception {

        // act
        return restSession.put("/api/v1/registration/" + registrationInfo.getPendingRegistrationId(),
                VerificationCode.of(verificationCode));
    }

    @Test
    void registerWithCorrectCode_succeeds() throws Exception {

        // arrange
        ResponseEntity<PendingRegistrationInfo> pendingRegistrationResponse = postSomeInitialRegistrationData();
        assumeTrue(pendingRegistrationResponse.getBody() != null);

        ResponseEntity<Void> confirmationResponse = registerWithCode(
                pendingRegistrationResponse.getBody(),
                FixVerificationCodeProvider.FIX_VERIFICATION_CODE
        );

        // assert
        assertEquals(HttpStatus.OK, confirmationResponse.getStatusCode());
    }

    @Test
    void afterRegistration_userIsAuthenticated() throws Exception {

        // arrange
        ResponseEntity<PendingRegistrationInfo> pendingRegistrationResponse = postSomeInitialRegistrationData();
        assumeTrue(pendingRegistrationResponse.getBody() != null);
        String expectedPrincipal = pendingRegistrationResponse.getBody().getPendingRegistrationId().toString();

        ResponseEntity<Void> confirmationResponse = registerWithCode(
                pendingRegistrationResponse.getBody(),
                FixVerificationCodeProvider.FIX_VERIFICATION_CODE
        );
        assumeTrue(confirmationResponse.getStatusCode() == HttpStatus.OK);

        // act
        var result = restSession.get("/api/v1/session/", SessionInfo.class);

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isAuthenticated());
        assertEquals(
                expectedPrincipal,
                result.getBody().getPrincipalName());
    }
}
