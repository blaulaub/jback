package ch.patchcode.jback.api.registration;

import ch.patchcode.jback.api.ApiTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
        var result = mvc.perform(post("/ch.patchcode.jback.sec.registration")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(content));

        // assert (only an API test, so no end-to-end testing, no testing of error handling)
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pendingRegistrationId").exists());
    }

    @Test
    void putVerificationCode() throws Exception {

        // arrange
        String content = "{ \"verificationCode\": \"1234\"}";

        // act
        var result = mvc.perform(put("/ch.patchcode.jback.sec.registration/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(content));

        // assert (only an API test, so no end-to-end testing, no testing of error handling)
        result.andExpect(status().isOk());
    }
}
