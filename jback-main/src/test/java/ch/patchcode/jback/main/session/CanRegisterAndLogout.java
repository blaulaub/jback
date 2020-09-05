package ch.patchcode.jback.main.session;

import ch.patchcode.jback.api.registration.PendingRegistrationInfo;
import ch.patchcode.jback.api.registration.VerificationCode;
import ch.patchcode.jback.main.Main;
import ch.patchcode.jback.main.MainTestConfiguration;
import ch.patchcode.jback.main.fakes.FixVerificationCodeProvider;
import ch.patchcode.jback.main.util.SomeData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import static org.junit.jupiter.api.Assertions.*;

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
        var registrationInfo = restTemplate.postForEntity(
                baseUrl() + "/api/v1/registration",
                initialRegistrationData,
                PendingRegistrationInfo.class);

        // assert
        assertEquals(HttpStatus.OK, registrationInfo.getStatusCode());
        assertNotNull(registrationInfo.getBody());

        return registrationInfo;
    }

    @Test
    void postSomeInitialRegistrationData_works() {

        // act
        var result = postSomeInitialRegistrationData();

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void registerWithCorrectCode_succeeds() throws Exception {

        // arrange
        PendingRegistrationInfo registrationInfo = postSomeInitialRegistrationData().getBody();

        // act
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        var confirmationResponse = restTemplate.exchange(
                baseUrl() + "/api/v1/registration/" + registrationInfo.getPendingRegistrationId(),
                HttpMethod.PUT,
                new HttpEntity<>(
                        objectMapper.writeValueAsString(VerificationCode.of(FixVerificationCodeProvider.FIX_VERIFICATION_CODE)),
                        httpHeaders
                ),
                Void.class
        );

        // assert
        assertEquals(HttpStatus.OK, confirmationResponse.getStatusCode());
    }

    private String baseUrl() {
        return "http://localhost:" + port;
    }
}
