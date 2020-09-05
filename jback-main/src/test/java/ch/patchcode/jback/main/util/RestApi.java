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
        var response = restSession.post("/api/v1/registration", initialRegistrationData, PendingRegistrationInfo.class);
        return new RegistrationPostData(response);
    }

    public class RegistrationPostData {

        private final ResponseEntity<PendingRegistrationInfo> response;

        public RegistrationPostData(ResponseEntity<PendingRegistrationInfo> response) {
            this.response = response;
        }

        public ResponseEntity<PendingRegistrationInfo> andReturn() {
            return response;
        }

        public RegistrationPostData checkResultIsSuccess() {
            assertEquals(HttpStatus.OK, response.getStatusCode());
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
