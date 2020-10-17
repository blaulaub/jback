package ch.patchcode.jback.tests;

import ch.patchcode.jback.api.registration.PendingRegistrationInfo;
import ch.patchcode.jback.api.verification.VerificationCode;
import ch.patchcode.jback.testsInfra.Api;
import ch.patchcode.jback.testsInfra.ApiTestConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;

import static ch.patchcode.jback.testsInfra.ConstantVerificationCodeProvider.VERIFICATION_CODE;
import static ch.patchcode.jback.testsInfra.Some.initialRegistrationData;

@ApiTestConfiguration.Apply
class RegistrationTest {

    private final Api api;

    @Autowired
    public RegistrationTest(@LocalServerPort int port, ObjectMapper mapper, Environment env) {
        this.api = new Api(port, mapper, env);
    }

    @Test
    @DisplayName("posting initial registration data yields pending registration id")
    void postingRegistrationYieldsPendingRegistrationId() throws Exception {

        // arrange
        var content = initialRegistrationData();

        // act
        var result = api.postRegistration(content).andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.pendingRegistrationId").exists();
    }

    @Test
    @DisplayName("putting wrong verification code is forbidden")
    void puttingWrongVerificationCodeIsForbidden() throws Exception {

        // arrange
        final String wrongCode = VERIFICATION_CODE + 1;

        var info = api.postRegistration(initialRegistrationData())
                .andAssumeGoodAndReturn(PendingRegistrationInfo.class);

        // act
        var result = api.putVerificationCode(info.getPendingRegistrationId(), VerificationCode.of(wrongCode)).andReturn();

        // assert
        result.expectStatus().isForbidden();
    }

    @Test
    @DisplayName("putting correct verification code is ok")
    void puttingCorrectVerificationCodeIsOk() throws Exception {

        // arrange
        var info = api.postRegistration(initialRegistrationData())
                .andAssumeGoodAndReturn(PendingRegistrationInfo.class);

        // act
        var result = api.putVerificationCode(
                info.getPendingRegistrationId(),
                VerificationCode.of(VERIFICATION_CODE)
        ).andReturn();

        // assert
        result.expectStatus().isOk();
    }
}
