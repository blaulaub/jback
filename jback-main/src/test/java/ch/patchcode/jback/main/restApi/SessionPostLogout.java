package ch.patchcode.jback.main.restApi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionPostLogout implements RestApiInvocationResult<SessionPostLogout, Void> {

    private final ResponseEntity<Void> response;

    public SessionPostLogout(ResponseEntity<Void> response) {
        this.response = response;
    }

    @Override
    public SessionPostLogout checkResultIsSuccess() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        return this;
    }

    @Override
    public ResponseEntity<Void> andReturn() {
        return response;
    }
}
