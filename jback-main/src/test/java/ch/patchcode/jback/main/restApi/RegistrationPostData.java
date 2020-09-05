package ch.patchcode.jback.main.restApi;

import ch.patchcode.jback.api.registration.PendingRegistrationInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationPostData implements RestApiInvocationResult<RegistrationPostData, PendingRegistrationInfo> {

    private final ResponseEntity<PendingRegistrationInfo> response;

    public RegistrationPostData(ResponseEntity<PendingRegistrationInfo> response) {
        this.response = response;
    }

    @Override
    public RegistrationPostData checkResultIsSuccess() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        return this;
    }

    @Override
    public ResponseEntity<PendingRegistrationInfo> andReturn() {
        return response;
    }
}
