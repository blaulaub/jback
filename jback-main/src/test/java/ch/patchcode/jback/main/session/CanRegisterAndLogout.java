package ch.patchcode.jback.main.session;

import ch.patchcode.jback.api.registration.InitialRegistrationData;
import ch.patchcode.jback.api.registration.PendingRegistrationInfo;
import ch.patchcode.jback.api.registration.VerificationCode;
import ch.patchcode.jback.api.registration.VerificationMean;
import ch.patchcode.jback.main.Main;
import ch.patchcode.jback.main.MainTestConfiguration;
import ch.patchcode.jback.main.fakes.FixVerificationCodeProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextHierarchy({
        @ContextConfiguration(classes = {MainTestConfiguration.class}),
        @ContextConfiguration(classes = {Main.class})
})
public class CanRegisterAndLogout {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void registerWithCorrectCode_succeeds() throws Exception {

        // arrange
        var initialRegistrationData = new InitialRegistrationData.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationMean(new VerificationMean.VerificationByConsole.Builder().build())
                .build();

        // act - request registration
        var registrationInfo = restTemplate.postForEntity(
                baseUrl() + "/api/v1/registration",
                initialRegistrationData,
                PendingRegistrationInfo.class);

        // assert
        assertEquals(HttpStatus.OK, registrationInfo.getStatusCode());

        // act - verify registration
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        var confirmationResponse = restTemplate.exchange(
                baseUrl() + "/api/v1/registration/" + registrationInfo.getBody().getPendingRegistrationId(),
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
