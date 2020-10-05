package ch.patchcode.jback.test;

import ch.patchcode.jback.api.registration.PendingRegistrationInfo;
import ch.patchcode.jback.api.verification.VerificationCode;
import ch.patchcode.jback.presentation.Perspective;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static ch.patchcode.jback.test.ConstantVerificationCodeProvider.VERIFICATION_CODE;
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
    @DisplayName("after putting correct verification session becomes XXX")
    void afterPuttingCorrectVerificationSessionBecomesXXX() throws Exception {

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
