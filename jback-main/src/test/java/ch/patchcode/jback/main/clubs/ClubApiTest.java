package ch.patchcode.jback.main.clubs;

import ch.patchcode.jback.main.MainTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MainTestConfiguration.Apply
class ClubApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void get_anyClub_whenNoneExists_returns404() {

        // act
        var response = restTemplate.getForEntity(
                baseUrl() + "/api/v1/clubs/" + UUID.randomUUID(),
                Object.class
        );

        // assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private String baseUrl() {
        return "http://localhost:" + port;
    }
}
