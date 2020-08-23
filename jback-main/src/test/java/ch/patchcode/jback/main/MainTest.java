package ch.patchcode.jback.main;

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
    public void get_anyClub_whenNoneExists_returns404() {

        // act
        var response = restTemplate.getForEntity(
                "http://localhost:" + port + "/clubs/" + UUID.randomUUID(),
                Object.class
        );

        // assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
