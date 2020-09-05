package ch.patchcode.jback.main.session;

import ch.patchcode.jback.api.registration.PendingRegistrationInfo;
import ch.patchcode.jback.api.registration.VerificationCode;
import ch.patchcode.jback.api.session.SessionInfo;
import ch.patchcode.jback.main.MainTestConfiguration;
import ch.patchcode.jback.main.fakes.FixVerificationCodeProvider;
import ch.patchcode.jback.main.util.SomeData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {MainTestConfiguration.class})
class CanRegisterAndLogout {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    private ResponseEntity<PendingRegistrationInfo> postSomeInitialRegistrationData() {

        // arrange
        var initialRegistrationData = SomeData.someInitialRegistrationData();

        // act - request registration
        return restTemplate.postForEntity(
                baseUrl() + "/api/v1/registration",
                initialRegistrationData,
                PendingRegistrationInfo.class);
    }

    @Test
    void postSomeInitialRegistrationData_works() {

        // act
        var result = postSomeInitialRegistrationData();

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    private ResponseEntity<Void> registerWithCode(PendingRegistrationInfo registrationInfo, String verificationCode)
            throws JsonProcessingException {

        // arrange
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // act
        return restTemplate.exchange(
                baseUrl() + "/api/v1/registration/" + registrationInfo.getPendingRegistrationId(),
                HttpMethod.PUT,
                new HttpEntity<>(
                        objectMapper.writeValueAsString(VerificationCode.of(verificationCode)),
                        httpHeaders
                ),
                Void.class
        );
    }

    @Test
    void registerWithCorrectCode_succeeds() throws Exception {

        // arrange
        ResponseEntity<PendingRegistrationInfo> pendingRegistrationResponse = postSomeInitialRegistrationData();
        assumeTrue(pendingRegistrationResponse.getBody() != null);

        ResponseEntity<Void> confirmationResponse = registerWithCode(
                pendingRegistrationResponse.getBody(),
                FixVerificationCodeProvider.FIX_VERIFICATION_CODE
        );

        // assert
        assertEquals(HttpStatus.OK, confirmationResponse.getStatusCode());
    }

    @Test
    void afterRegistration_userIsAuthenticated() throws Exception {

        // arrange
        ResponseEntity<PendingRegistrationInfo> pendingRegistrationResponse = postSomeInitialRegistrationData();
        assumeTrue(pendingRegistrationResponse.getBody() != null);

        ResponseEntity<Void> confirmationResponse = registerWithCode(
                pendingRegistrationResponse.getBody(),
                FixVerificationCodeProvider.FIX_VERIFICATION_CODE
        );
        assumeTrue(confirmationResponse.getStatusCode() == HttpStatus.OK);

        // act
        var result = restTemplate.getForEntity(baseUrl() + "/api/v1/session/", SessionInfo.class);

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isAuthenticated());
        // TODO this is the default, we will replace that with our own
        assertEquals("anonymousUser", result.getBody().getPrincipalName());
    }

    private String baseUrl() {
        return "http://localhost:" + port;
    }
}
