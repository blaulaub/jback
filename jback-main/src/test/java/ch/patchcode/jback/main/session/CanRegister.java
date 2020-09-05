package ch.patchcode.jback.main.session;

import ch.patchcode.jback.api.registration.PendingRegistrationInfo;
import ch.patchcode.jback.api.registration.VerificationCode;
import ch.patchcode.jback.main.MainTestConfiguration;
import ch.patchcode.jback.main.fakes.FixVerificationCodeProvider;
import ch.patchcode.jback.main.util.RestApi;
import ch.patchcode.jback.main.util.RestSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static ch.patchcode.jback.main.util.SomeData.someInitialRegistrationData;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {MainTestConfiguration.class})
class CanRegister {

    private final RestApi api;

    @Autowired
    public CanRegister(@LocalServerPort int port, TestRestTemplate restTemplate, ObjectMapper objectMapper) {
        this.api = new RestApi(new RestSession(port, restTemplate, objectMapper));
    }

    @Test
    void postSomeInitialRegistrationData_works() throws Exception {

        // act
        var result = api.registrationPostData().andReturn(someInitialRegistrationData());

        // assert
        api.registrationPostData().checkResultIsSuccess(result);
        assertNotNull(result.getBody());
    }

    @Test
    void registerWithCorrectCode_succeeds() throws Exception {

        // arrange
        var pendingRegistrationResponse = api.registrationPostData().andReturn(someInitialRegistrationData());
        assumeTrue(HttpStatus.OK == pendingRegistrationResponse.getStatusCode());
        assumeTrue(pendingRegistrationResponse.getBody() != null);

        // act
        VerificationCode code = VerificationCode.of(FixVerificationCodeProvider.FIX_VERIFICATION_CODE);
        ResponseEntity<Void> confirmationResponse = api.registrationPutCode().andReturn(pendingRegistrationResponse.getBody(), code);

        // assert
        api.registrationPutCode().checkResultIsSuccess(confirmationResponse);
    }

    @Test
    void afterRegistration_userIsAuthenticated() throws Exception {

        // arrange
        ResponseEntity<PendingRegistrationInfo> pendingRegistrationResponse = api.registrationPostData().andReturn(someInitialRegistrationData());
        assumeTrue(pendingRegistrationResponse.getBody() != null);
        String expectedPrincipal = pendingRegistrationResponse.getBody().getPendingRegistrationId().toString();

        // act
        VerificationCode code = VerificationCode.of(FixVerificationCodeProvider.FIX_VERIFICATION_CODE);
        ResponseEntity<Void> confirmationResponse = api.registrationPutCode().andReturn(pendingRegistrationResponse.getBody(), code);
        assumeTrue(confirmationResponse.getStatusCode() == HttpStatus.OK);

        // act
        var result = api.sessionGet().andReturn();

        // assert
        api.sessionGet().checkResultIsSuccess(result);
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isAuthenticated());
        assertEquals(
                expectedPrincipal,
                result.getBody().getPrincipalName());
    }

}
