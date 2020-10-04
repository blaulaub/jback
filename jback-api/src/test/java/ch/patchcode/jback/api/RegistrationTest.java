package ch.patchcode.jback.api;

import ch.patchcode.jback.api.registration.InitialRegistrationData;
import ch.patchcode.jback.api.verification.VerificationByConsole;
import ch.patchcode.jback.presentation.Perspective;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ApiTestConfiguration.Apply
class RegistrationTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper;

    @Autowired
    public RegistrationTest(MockMvc mvc, ObjectMapper mapper) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    @Test
    @DisplayName("posting registration yields pending registration id")
    void postingRegistrationYieldsPendingRegistrationId() throws Exception {

        // arrange
        var content = InitialRegistrationData.create(
                "Gregor",
                "McGee",
                new VerificationByConsole.Draft.Builder().build()
        );

        // act
        var result = mvc.perform(post("/api/v1/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(content)));

        // assert
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pendingRegistrationId").exists());
    }
}
