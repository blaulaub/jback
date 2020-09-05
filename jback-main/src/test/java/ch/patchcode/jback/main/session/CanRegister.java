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

    private final Api api;

    @Autowired
    public CanRegister(@LocalServerPort int port, TestRestTemplate restTemplate, ObjectMapper objectMapper) {
        this.api = new Api(new RestSession(port, restTemplate, objectMapper));
    }

    @Test
    void postSomeInitialRegistrationData_works() throws Exception {

        // act
        var result = api.registrationPostData().andReturn(someInitialRegistrationData());

        // assert
        api.registrationPostData().checkResultIsSuccess(result);
        assertNotNull(result.getBody());
    }

    @Test
    void registerWithCorrectCode_succeeds() throws Exception {

        // arrange
        var pendingRegistrationResponse = api.registrationPostData().andReturn(someInitialRegistrationData());
        assumeTrue(HttpStatus.OK == pendingRegistrationResponse.getStatusCode());
        assumeTrue(pendingRegistrationResponse.getBody() != null);

        // act
        VerificationCode code = VerificationCode.of(FixVerificationCodeProvider.FIX_VERIFICATION_CODE);
        ResponseEntity<Void> confirmationResponse = api.registrationPutCode().andReturn(pendingRegistrationResponse.getBody(), code);

        // assert
        api.registrationPutCode().checkResultIsSuccess(confirmationResponse);
    }

    @Test
    void afterRegistration_userIsAuthenticated() throws Exception {

        // arrange
        ResponseEntity<PendingRegistrationInfo> pendingRegistrationResponse = api.registrationPostData().andReturn(someInitialRegistrationData());
        assumeTrue(pendingRegistrationResponse.getBody() != null);
        String expectedPrincipal = pendingRegistrationResponse.getBody().getPendingRegistrationId().toString();

        // act
        VerificationCode code = VerificationCode.of(FixVerificationCodeProvider.FIX_VERIFICATION_CODE);
        ResponseEntity<Void> confirmationResponse = api.registrationPutCode().andReturn(pendingRegistrationResponse.getBody(), code);
        assumeTrue(confirmationResponse.getStatusCode() == HttpStatus.OK);

        // act
        var result = api.sessionGet().andReturn();

        // assert
        api.sessionGet().checkResultIsSuccess(result);
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isAuthenticated());
        assertEquals(
                expectedPrincipal,
                result.getBody().getPrincipalName());
    }

    public static class Api {

        private final RestSession restSession;

        public Api(RestSession restSession) {
            this.restSession = restSession;
        }

        public RegistrationPostData registrationPostData() {
            return new RegistrationPostData();
        }

        public class RegistrationPostData {

            private ResponseEntity<PendingRegistrationInfo> andReturn(ch.patchcode.jback.api.registration.InitialRegistrationData initialRegistrationData) throws Exception {
                return restSession.post("/api/v1/registration", initialRegistrationData, PendingRegistrationInfo.class);
            }

            private void checkResultIsSuccess(ResponseEntity<PendingRegistrationInfo> result) {
                assertEquals(HttpStatus.OK, result.getStatusCode());
            }
        }

        public RegistrationPutCode registrationPutCode() {
            return new RegistrationPutCode();
        }

        public class RegistrationPutCode {

            private ResponseEntity<Void> andReturn(PendingRegistrationInfo registrationInfo, VerificationCode code) throws Exception {
                return restSession.put("/api/v1/registration/" + registrationInfo.getPendingRegistrationId(), code);
            }

            private void checkResultIsSuccess(ResponseEntity<Void> result) {
                assertEquals(HttpStatus.OK, result.getStatusCode());
            }
        }

        public SessionGet sessionGet() {
            return new SessionGet();
        }

        public class SessionGet {

            private ResponseEntity<SessionInfo> andReturn() {
                return restSession.get("/api/v1/session/", SessionInfo.class);
            }

            private void checkResultIsSuccess(ResponseEntity<SessionInfo> result) {
                assertEquals(HttpStatus.OK, result.getStatusCode());
            }
        }
    }
}
