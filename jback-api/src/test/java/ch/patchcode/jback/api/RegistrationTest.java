package ch.patchcode.jback.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ApiTestConfiguration.Apply
class RegistrationTest {

    private final Api api;


    @Autowired
    public RegistrationTest(Api api) {
        this.api = api;
    }

    @Test
    @DisplayName("posting registration yields pending registration id")
    void postingRegistrationYieldsPendingRegistrationId() throws Exception {

        // arrange
        var content = Some.initialRegistrationData();

        // act
        var result = api.postRegistration(content).andReturn();

        // assert
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pendingRegistrationId").exists());
    }
}
