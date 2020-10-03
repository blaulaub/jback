package ch.patchcode.jback.main.session;

import ch.patchcode.jback.api.persons.Person;
import ch.patchcode.jback.api.verification.VerificationCode;
import ch.patchcode.jback.main.MainTestConfiguration;
import ch.patchcode.jback.main.fakes.FixVerificationCodeProvider;
import ch.patchcode.jback.main.restApi.RestApi;
import ch.patchcode.jback.main.util.RestSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static ch.patchcode.jback.main.util.SomeData.someInitialRegistrationData;

@MainTestConfiguration.Apply
public class CreatedPersonBecomesSessionUser {

    private final RestApi api;

    @Autowired
    public CreatedPersonBecomesSessionUser(
            @LocalServerPort int port,
            TestRestTemplate restTemplate,
            ObjectMapper objectMapper
    ) {
        this.api = new RestApi(new RestSession(port, restTemplate, objectMapper));
    }

    @Test
    void afterCreatingPerson_personBecomesSessionUser() throws Exception {

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
        var createdPerson = api.personsPostNewMe(newPerson)
                .checkResultIsSuccess()
                .andReturnBody();

        // get session
        var sessionInfo = api.sessionGet()
                .checkResultIsSuccess()
                .andReturnBody();

        // TODO assert session indicates that user is new person
    }
}
