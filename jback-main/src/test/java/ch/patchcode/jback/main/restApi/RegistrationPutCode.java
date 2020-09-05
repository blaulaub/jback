package ch.patchcode.jback.main.restApi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationPutCode {

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
