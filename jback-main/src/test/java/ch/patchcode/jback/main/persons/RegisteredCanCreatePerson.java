package ch.patchcode.jback.main.persons;

import ch.patchcode.jback.api.persons.Person;
import ch.patchcode.jback.api.registration.VerificationCode;
import ch.patchcode.jback.main.MainTestConfiguration;
import ch.patchcode.jback.main.fakes.FixVerificationCodeProvider;
import ch.patchcode.jback.main.restApi.RestApi;
import ch.patchcode.jback.main.util.RestSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

import static ch.patchcode.jback.main.util.SomeData.someInitialRegistrationData;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {MainTestConfiguration.class})
public class RegisteredCanCreatePerson {

    private final RestApi api;

    @Autowired
    public RegisteredCanCreatePerson(@LocalServerPort int port, TestRestTemplate restTemplate, ObjectMapper objectMapper) {
        this.api = new RestApi(new RestSession(port, restTemplate, objectMapper));
    }

    @Test
    void afterRegistration_creatingPerson_succeeds() throws Exception {

        Person.Draft newPerson = new Person.Draft.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .build();

        // post registration
        var pendingRegistration = api.registrationPostData(someInitialRegistrationData())
                .checkResultIsSuccess()
                .andReturnBody();

        // put code
        api.registrationPutCode(
                pendingRegistration,
                VerificationCode.of(FixVerificationCodeProvider.FIX_VERIFICATION_CODE))
                .checkResultIsSuccess();

        // create person
        var createdPerson = api.personsPostNewPerson(newPerson)
                .checkResultIsSuccess()
                .andReturnBody();

    }

}