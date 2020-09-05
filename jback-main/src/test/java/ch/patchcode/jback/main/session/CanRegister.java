package ch.patchcode.jback.main.session;

import ch.patchcode.jback.api.registration.PendingRegistrationInfo;
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
        var invocation = api.registrationPostData(someInitialRegistrationData());

        // assert
        invocation.checkResultIsSuccess();
        assertNotNull(invocation.andReturn().getBody());
    }

    @Test
    void registerWithCorrectCode_succeeds() throws Exception {

        // arrange
        var pendingRegistrationResponse = api.registrationPostData(someInitialRegistrationData()).andReturn();
        assumeTrue(HttpStatus.OK == pendingRegistrationResponse.getStatusCode());
        assumeTrue(pendingRegistrationResponse.getBody() != null);

        // act
        VerificationCode code = VerificationCode.of(FixVerificationCodeProvider.FIX_VERIFICATION_CODE);
        var invocation = api.registrationPutCode(pendingRegistrationResponse.getBody(), code);

        // assert
        invocation.checkResultIsSuccess();
    }

    @Test
    void afterRegistration_userIsAuthenticated() throws Exception {

        // arrange
        ResponseEntity<PendingRegistrationInfo> pendingRegistrationResponse = api.registrationPostData(someInitialRegistrationData()).andReturn();
        assumeTrue(pendingRegistrationResponse.getBody() != null);
        String expectedPrincipal = pendingRegistrationResponse.getBody().getPendingRegistrationId().toString();

        // act
        VerificationCode code = VerificationCode.of(FixVerificationCodeProvider.FIX_VERIFICATION_CODE);
        ResponseEntity<Void> confirmationResponse = api.registrationPutCode(pendingRegistrationResponse.getBody(), code).andReturn();
        assumeTrue(confirmationResponse.getStatusCode() == HttpStatus.OK);

        // act
        var invocation = api.sessionGet();

        // assert
        invocation.checkResultIsSuccess();
        assertNotNull(invocation.andReturn().getBody());
        assertTrue(invocation.andReturn().getBody().isAuthenticated());
        assertEquals(
                expectedPrincipal,
                invocation.andReturn().getBody().getPrincipalName());
    }

}
