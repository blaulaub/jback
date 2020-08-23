package ch.patchcode.jback.main;

import ch.patchcode.jback.main.test.repositories.TestRepositoriesConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextHierarchy({
        /* <-- insert test repo config here before Main.class */
        @ContextConfiguration(classes = {Main.class})
})
class MainTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {

        // act
        var x = this.restTemplate.getForObject("http://localhost:" + port + "/", String.class);

        // assert
        Assertions.assertTrue(x.contains("Hello World"));
    }
}