package ch.patchcode.jback.main.registration;

import ch.patchcode.jback.api.registration.InitialRegistrationData;
import ch.patchcode.jback.api.registration.PendingRegistrationInfo;
import ch.patchcode.jback.api.registration.VerificationMean;
import ch.patchcode.jback.main.Main;
import ch.patchcode.jback.main.MainTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MainTestConfiguration.Apply
public class CanSubmitDifferentRegistrationsTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void put_consoleRegistration() {

        // arrange
        var request = new InitialRegistrationData.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationMean(new VerificationMean.VerificationByConsole.Builder().build())
                .build();

        // act
        var response = restTemplate.postForEntity(
                baseUrl() + "/api/v1/registration",
                request,
                PendingRegistrationInfo.class
        );

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getPendingRegistrationId());
    }

    @Test
    public void put_smsRegistration() {

        // arrange
        var request = new InitialRegistrationData.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationMean(new VerificationMean.VerificationBySms.Builder().setPhoneNumber("+41234567890").build())
                .build();

        // act
        var response = restTemplate.postForEntity(
                baseUrl() + "/api/v1/registration",
                request,
                PendingRegistrationInfo.class
        );

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getPendingRegistrationId());
    }

    @Test
    public void put_emailRegistration() {

        // arrange
        var request = new InitialRegistrationData.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationMean(new VerificationMean.VerificationByEmail.Builder().setEmailAddress("webmaster@google.com").build())
                .build();

        // act
        var response = restTemplate.postForEntity(
                baseUrl() + "/api/v1/registration",
                request,
                PendingRegistrationInfo.class
        );

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getPendingRegistrationId());
    }

    private String baseUrl() {
        return "http://localhost:" + port;
    }
}
