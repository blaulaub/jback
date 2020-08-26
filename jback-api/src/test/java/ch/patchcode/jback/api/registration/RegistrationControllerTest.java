package ch.patchcode.jback.api.registration;

import ch.patchcode.jback.api.ApiTestConfiguration;
import ch.patchcode.jback.core.registration.InitialRegistrationData;
import ch.patchcode.jback.core.registration.VerificationMean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrationController.class)
@ContextConfiguration(classes = {ApiTestConfiguration.class})
class RegistrationControllerTest {

    private final MockMvc mvc;

    @Autowired
    public RegistrationControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void postInitialRegistration() throws Exception {

        // arrange
        String content = "{" +
                "\"firstName\": \"Tom\"," +
                "\"lastName\": \"Sawyer\"," +
                "\"verificationMean\":" +
                "{ \"type\": \"console\" }" +
                "}";

        // act
        var result = mvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(content));

        // assert
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pendingRegistrationId").exists());
    }
}
