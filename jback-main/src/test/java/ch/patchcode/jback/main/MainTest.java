package ch.patchcode.jback.main;

import ch.patchcode.jback.api.registration.InitialRegistrationData;
import ch.patchcode.jback.api.registration.VerificationMean;
import ch.patchcode.jback.api.registration.VerificationMean.VerificationBySms;
import ch.patchcode.jback.api.registration.VerificationMean.VerificationByConsole;
import ch.patchcode.jback.api.registration.VerificationMean.VerificationByEmail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextHierarchy({
        @ContextConfiguration(classes = {MainTestConfiguration.class}),
        @ContextConfiguration(classes = {Main.class})
})
class MainTest {

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
                .setContactMean(new VerificationByConsole.Builder().build())
                .build();

        // act
        var response = restTemplate.postForEntity(
                baseUrl() + "/registration",
                request,
                Object.class
        );

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void put_smsRegistration() {

        // arrange
        var request = new InitialRegistrationData.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setContactMean(new VerificationBySms.Builder().setPhoneNumber("+41234567890").build())
                .build();

        // act
        var response = restTemplate.postForEntity(
                baseUrl() + "/registration",
                request,
                Object.class
        );

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void put_emailRegistration() {

        // arrange
        var request = new InitialRegistrationData.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setContactMean(new VerificationByEmail.Builder().setEmailAddress("webmaster@google.com").build())
                .build();

        // act
        var response = restTemplate.postForEntity(
                baseUrl() + "/registration",
                request,
                Object.class
        );

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void get_anyClub_whenNoneExists_returns404() {

        // act
        var response = restTemplate.getForEntity(
                baseUrl() + "/clubs/" + UUID.randomUUID(),
                Object.class
        );

        // assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private String baseUrl() {
        return "http://localhost:" + port;
    }
}
