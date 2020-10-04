package ch.patchcode.jback.api;

import ch.patchcode.jback.api.registration.PendingRegistrationInfo;
import ch.patchcode.jback.api.verification.VerificationCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static ch.patchcode.jback.api.ConstantVerificationCodeProvider.VERIFICATION_CODE;
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
    @DisplayName("posting initial registration data yields pending registration id")
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

    @Test
    void postingVerificationCode() throws Exception {

        // arrange
        var content = Some.initialRegistrationData();
        var info = api.postRegistration(content).andAssumeGoodAndReturn(PendingRegistrationInfo.class);

        // act
        var result = api.putVerificationCode(
                info.getPendingRegistrationId(),
                VerificationCode.of(VERIFICATION_CODE)
        ).andReturn();

        // assert
        result.andExpect(status().isOk());
    }
}
