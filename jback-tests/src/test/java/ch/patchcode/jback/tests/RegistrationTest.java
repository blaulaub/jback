package ch.patchcode.jback.tests;

import ch.patchcode.jback.api.registration.PendingRegistrationInfo;
import ch.patchcode.jback.api.verification.VerificationCode;
import ch.patchcode.jback.presentation.Perspective;
import ch.patchcode.jback.testsInfra.Api;
import ch.patchcode.jback.testsInfra.ApiTestConfiguration;
import ch.patchcode.jback.testsInfra.Some;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static ch.patchcode.jback.testsInfra.ConstantVerificationCodeProvider.VERIFICATION_CODE;
import static org.hamcrest.Matchers.equalTo;

@ApiTestConfiguration.Apply
class RegistrationTest {

    private final Api api;

    @Autowired
    public RegistrationTest(@LocalServerPort int port, ObjectMapper mapper) {
        this.api = new Api(port, mapper);
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
                .expectStatus().isOk()
                .expectBody().jsonPath("$.pendingRegistrationId").exists();
    }

    @Test
    @DisplayName("putting wrong verification code is forbidden")
    void puttingWrongVerificationCodeIsForbidden() throws Exception {

        // arrange
        final String wrongCode = VERIFICATION_CODE + 1;

        var content = Some.initialRegistrationData();
        var info = api.postRegistration(content).andAssumeGoodAndReturn(PendingRegistrationInfo.class);

        // act
        var result = api.putVerificationCode(info.getPendingRegistrationId(), VerificationCode.of(wrongCode)).andReturn();

        // assert
        result.expectStatus().isForbidden();
    }

    @Test
    @DisplayName("putting correct verification code is ok")
    void puttingCorrectVerificationCodeIsOk() throws Exception {

        // arrange
        var content = Some.initialRegistrationData();
        var info = api.postRegistration(content).andAssumeGoodAndReturn(PendingRegistrationInfo.class);

        // act
        var result = api.putVerificationCode(
                info.getPendingRegistrationId(),
                VerificationCode.of(VERIFICATION_CODE)
        ).andReturn();

        // assert
        result.expectStatus().isOk();
    }

    @Test
    @DisplayName("after putting wrong verification perspective remains GUEST")
    void afterPuttingWrongVerificationSessionRemainsGuest() throws Exception {

        // arrange
        final String wrongCode = VERIFICATION_CODE + 1;

        var content = Some.initialRegistrationData();
        var info = api.postRegistration(content).andAssumeGoodAndReturn(PendingRegistrationInfo.class);
        api.putVerificationCode(info.getPendingRegistrationId(), VerificationCode.of(wrongCode));

        // act
        var result = api.getSession().andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.perspective").value(equalTo(Perspective.GUEST.toString()));
    }

    @Test
    @DisplayName("after putting correct verification perspective becomes ENROLLING")
    void afterPuttingCorrectVerificationSessionBecomesEnrolling() throws Exception {

        // arrange
        var content = Some.initialRegistrationData();
        var info = api.postRegistration(content).andAssumeGoodAndReturn(PendingRegistrationInfo.class);
        api.putVerificationCode(info.getPendingRegistrationId(), VerificationCode.of(VERIFICATION_CODE)).andAssumeGoodAndReturn();

        // act
        var result = api.getSession().andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.perspective").value(equalTo(Perspective.ENROLLING.toString()));
    }
}
