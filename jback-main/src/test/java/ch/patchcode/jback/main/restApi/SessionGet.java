package ch.patchcode.jback.main.restApi;

import ch.patchcode.jback.api.session.SessionInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionGet implements RestApiInvocationResult<SessionGet, SessionInfo> {

    private final ResponseEntity<SessionInfo> response;

    public SessionGet(ResponseEntity<SessionInfo> response) {
        this.response = response;
    }

    @Override
    public SessionGet checkResultIsSuccess() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        return this;
    }

    @Override
    public ResponseEntity<SessionInfo> andReturn() {
        return response;
    }
}
