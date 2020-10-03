package ch.patchcode.jback.main.session;

import ch.patchcode.jback.api.registration.InitialRegistrationData;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MainTestConfiguration.Apply
class AnybodyCanRegister {

    private final RestApi api;

    @Autowired
    public AnybodyCanRegister(@LocalServerPort int port, TestRestTemplate restTemplate, ObjectMapper objectMapper) {
        this.api = new RestApi(new RestSession(port, restTemplate, objectMapper));
    }

    @Test
    void afterRegistration_userIsAuthenticated() throws Exception {

        // post registration
        InitialRegistrationData initialData = someInitialRegistrationData();
        var pendingRegistration = api.registrationPostData(initialData)
                .checkResultIsSuccess()
                .andReturnBody();

        // put code
        api.registrationPutCode(
                pendingRegistration,
                VerificationCode.of(FixVerificationCodeProvider.FIX_VERIFICATION_CODE))
                .checkResultIsSuccess();

        // get session
        var sessionInfo = api.sessionGet()
                .checkResultIsSuccess()
                .andReturnBody();

        // assert
        assertTrue(sessionInfo.isAuthenticated());
        assertEquals(
                initialData.getFirstName() + " " + initialData.getLastName(),
                sessionInfo.getPrincipalName());
    }
}
