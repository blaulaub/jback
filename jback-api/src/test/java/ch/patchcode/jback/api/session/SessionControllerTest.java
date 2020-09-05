package ch.patchcode.jback.api.session;

import ch.patchcode.jback.api.ApiTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SessionController.class)
@ContextConfiguration(classes = {ApiTestConfiguration.class})
class SessionControllerTest {

    private final MockMvc mvc;

    @Autowired
    public SessionControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void logout_isCallable() throws Exception {

        // act
        var result = mvc.perform(post("/api/v1/session/logout"));

        // assert
        result
                .andExpect(status().isOk());
    }
}
