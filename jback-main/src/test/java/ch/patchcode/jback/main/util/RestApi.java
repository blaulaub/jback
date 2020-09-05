package ch.patchcode.jback.main.util;

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

    public RegistrationPostData registrationPostData() {
        return new RegistrationPostData();
    }

    public class RegistrationPostData {

        private ResponseEntity<PendingRegistrationInfo> response;

        private void invoke(ch.patchcode.jback.api.registration.InitialRegistrationData initialRegistrationData) throws Exception {
            response = restSession.post("/api/v1/registration", initialRegistrationData, PendingRegistrationInfo.class);
        }

        public ResponseEntity<PendingRegistrationInfo> andReturn(ch.patchcode.jback.api.registration.InitialRegistrationData initialRegistrationData) throws Exception {
            invoke(initialRegistrationData);
            return response;
        }

        public RegistrationPostData checkResultIsSuccess(ResponseEntity<PendingRegistrationInfo> result) {
            assertEquals(HttpStatus.OK, result.getStatusCode());
            return this;
        }
    }

    public RegistrationPutCode registrationPutCode() {
        return new RegistrationPutCode();
    }

    public class RegistrationPutCode {

        private ResponseEntity<Void> response;

        private void invoke(PendingRegistrationInfo registrationInfo, VerificationCode code) throws Exception {
            response = restSession.put("/api/v1/registration/" + registrationInfo.getPendingRegistrationId(), code);
        }

        public ResponseEntity<Void> andReturn(PendingRegistrationInfo registrationInfo, VerificationCode code) throws Exception {
            invoke(registrationInfo, code);
            return response;
        }

        public RegistrationPutCode checkResultIsSuccess(ResponseEntity<Void> result) {
            assertEquals(HttpStatus.OK, result.getStatusCode());
            return this;
        }
    }

    public SessionGet sessionGet() {
        return new SessionGet();
    }

    public class SessionGet {

        private ResponseEntity<SessionInfo> response;

        private void invoke() {
            response = restSession.get("/api/v1/session/", SessionInfo.class);
        }

        public ResponseEntity<SessionInfo> andReturn() {
            invoke();
            return response;
        }

        public SessionGet checkResultIsSuccess(ResponseEntity<SessionInfo> result) {
            assertEquals(HttpStatus.OK, result.getStatusCode());
            return this;
        }
    }
}
