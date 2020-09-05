package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.secBase.InitialRegistrationData;
import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.SecurityManager;
import ch.patchcode.jback.secBase.VerificationCode;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecurityManagerFake implements SecurityManager {

    @Override
    public PendingRegistration.Id setupRegistration(InitialRegistrationData data) {

        return PendingRegistration.Id.of(UUID.randomUUID());
    }

    @Override
    public void authenticate(PendingRegistration.Id registrationId, VerificationCode verificationCode) {

        // do nothing
    }
}