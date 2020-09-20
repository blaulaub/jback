package ch.patchcode.jback.api.session;

import ch.patchcode.jback.api.ApiTestConfiguration;
import ch.patchcode.jback.api.util.WithTemporaryAuthentication;
import ch.patchcode.jback.presentation.Perspective;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test
    @WithTemporaryAuthentication(firstName = "Tom", lastName = "Sawyer")
    void getSessionInfo_withTemporaryAuthentication_hasFirstAndLastName() throws Exception {

        // act
        var result = mvc.perform(get("/api/v1/session"));

        // assert
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Tom"))
                .andExpect(jsonPath("$.lastName").value("Sawyer"));
    }

    @Test
    @WithTemporaryAuthentication(firstName = "Tom", lastName = "Sawyer")
    void getSessionInfo_withTemporaryAuthentication_hasPerspectiveEnrolling() throws Exception {

        // act
        var result = mvc.perform(get("/api/v1/session"));

        // assert
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.perspective").value(Perspective.ENROLLING.toString()));
    }
}
