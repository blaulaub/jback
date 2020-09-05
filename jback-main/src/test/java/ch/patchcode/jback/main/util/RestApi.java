package ch.patchcode.jback.main.util;

import ch.patchcode.jback.api.registration.InitialRegistrationData;
import ch.patchcode.jback.api.registration.PendingRegistrationInfo;
import ch.patchcode.jback.api.registration.VerificationCode;
import ch.patchcode.jback.api.session.SessionInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestApi {

    private final RestSession restSession;

    public RestApi(RestSession restSession) {
        this.restSession = restSession;
    }

    public RegistrationPostData registrationPostData(InitialRegistrationData initialRegistrationData) throws Exception {
        return new RegistrationPostData(restSession.post(
                "/api/v1/registration",
                initialRegistrationData,
                PendingRegistrationInfo.class
        ));
    }

    public static class RegistrationPostData {

        private final ResponseEntity<PendingRegistrationInfo> response;

        public RegistrationPostData(ResponseEntity<PendingRegistrationInfo> response) {
            this.response = response;
        }

        public RegistrationPostData checkResultIsSuccess() {
            assertEquals(HttpStatus.OK, response.getStatusCode());
            return this;
        }

        public ResponseEntity<PendingRegistrationInfo> andReturn() {
            return response;
        }
    }

    public RegistrationPutCode registrationPutCode(
            PendingRegistrationInfo registrationInfo,
            VerificationCode code
    ) throws Exception {
        return new RegistrationPutCode(
                restSession.put("/api/v1/registration/" + registrationInfo.getPendingRegistrationId(), code)
        );
    }

    public static class RegistrationPutCode {

        private final ResponseEntity<Void> response;

        public RegistrationPutCode(ResponseEntity<Void> response) {
            this.response = response;
        }

        public RegistrationPutCode checkResultIsSuccess() {
            assertEquals(HttpStatus.OK, response.getStatusCode());
            return this;
        }

        public ResponseEntity<Void> andReturn() {
            return response;
        }
    }

    public SessionGet sessionGet() {
        return new SessionGet(restSession.get("/api/v1/session/", SessionInfo.class));
    }

    public class SessionGet {

        private final ResponseEntity<SessionInfo> response;

        public SessionGet(ResponseEntity<SessionInfo> response) {
            this.response = response;
        }

        public SessionGet checkResultIsSuccess() {
            assertEquals(HttpStatus.OK, response.getStatusCode());
            return this;
        }

        public ResponseEntity<SessionInfo> andReturn() {
            return response;
        }
    }
}
