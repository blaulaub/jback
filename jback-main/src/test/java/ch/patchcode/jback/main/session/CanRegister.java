package ch.patchcode.jback.main.session;

import ch.patchcode.jback.api.registration.PendingRegistrationInfo;
import ch.patchcode.jback.api.registration.VerificationCode;
import ch.patchcode.jback.api.session.SessionInfo;
import ch.patchcode.jback.main.MainTestConfiguration;
import ch.patchcode.jback.main.fakes.FixVerificationCodeProvider;
import ch.patchcode.jback.main.util.RestSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static ch.patchcode.jback.main.util.SomeData.someInitialRegistrationData;
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

    @Test
    void postSomeInitialRegistrationData_works() throws Exception {

        // act
        var result = registration_postData_andReturn(someInitialRegistrationData());

        // assert
        registration_postData_checkResultIsSuccess(result);
        assertNotNull(result.getBody());
    }

    @Test
    void registerWithCorrectCode_succeeds() throws Exception {

        // arrange
        var pendingRegistrationResponse = registration_postData_andReturn(someInitialRegistrationData());
        assumeTrue(HttpStatus.OK == pendingRegistrationResponse.getStatusCode());
        assumeTrue(pendingRegistrationResponse.getBody() != null);

        // act
        VerificationCode code = VerificationCode.of(FixVerificationCodeProvider.FIX_VERIFICATION_CODE);
        ResponseEntity<Void> confirmationResponse = registration_putCode_andReturn(pendingRegistrationResponse.getBody(), code);

        // assert
        registration_putCode_checkResultIsSuccess(confirmationResponse);
    }

    @Test
    void afterRegistration_userIsAuthenticated() throws Exception {

        // arrange
        ResponseEntity<PendingRegistrationInfo> pendingRegistrationResponse = registration_postData_andReturn(someInitialRegistrationData());
        assumeTrue(pendingRegistrationResponse.getBody() != null);
        String expectedPrincipal = pendingRegistrationResponse.getBody().getPendingRegistrationId().toString();

        // act
        VerificationCode code = VerificationCode.of(FixVerificationCodeProvider.FIX_VERIFICATION_CODE);
        ResponseEntity<Void> confirmationResponse = registration_putCode_andReturn(pendingRegistrationResponse.getBody(), code);
        assumeTrue(confirmationResponse.getStatusCode() == HttpStatus.OK);

        // act
        var result = session_get_andReturn();

        // assert
        session_get_checkResultIsSuccess(result);
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isAuthenticated());
        assertEquals(
                expectedPrincipal,
                result.getBody().getPrincipalName());
    }

    private ResponseEntity<PendingRegistrationInfo> registration_postData_andReturn(ch.patchcode.jback.api.registration.InitialRegistrationData initialRegistrationData) throws Exception {
        return restSession.post("/api/v1/registration", initialRegistrationData, PendingRegistrationInfo.class);
    }

    private void registration_postData_checkResultIsSuccess(ResponseEntity<PendingRegistrationInfo> result) {
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    private ResponseEntity<Void> registration_putCode_andReturn(PendingRegistrationInfo registrationInfo, VerificationCode code) throws Exception {
        return restSession.put("/api/v1/registration/" + registrationInfo.getPendingRegistrationId(), code);
    }

    private void registration_putCode_checkResultIsSuccess(ResponseEntity<Void> result) {
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    private ResponseEntity<SessionInfo> session_get_andReturn() {
        return restSession.get("/api/v1/session/", SessionInfo.class);
    }

    private void session_get_checkResultIsSuccess(ResponseEntity<SessionInfo> result) {
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
