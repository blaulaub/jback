package ch.patchcode.jback.main.restApi;

import ch.patchcode.jback.api.persons.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonPostNewPerson implements RestApiInvocationResult<PersonPostNewPerson, Person> {

    private final ResponseEntity<Person> response;

    public PersonPostNewPerson(ResponseEntity<Person> response) {
        this.response = response;
    }

    @Override
    public PersonPostNewPerson checkResultIsSuccess() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        return this;
    }

    @Override
    public ResponseEntity<Person> andReturn() {
        return response;
    }
}
