package ch.patchcode.jback.main.restApi;

import ch.patchcode.jback.api.persons.Person;
import org.springframework.http.ResponseEntity;

public class PersonPostNewPerson implements RestApiInvocationResult<PersonPostNewPerson, Person> {

    public PersonPostNewPerson(ResponseEntity<Person> post) {
    }

    @Override
    public PersonPostNewPerson checkResultIsSuccess() {
        return null;
    }

    @Override
    public ResponseEntity<Person> andReturn() {
        return null;
    }
}
