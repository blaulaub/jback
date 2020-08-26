package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.core.registration.ConfirmationResult;
import ch.patchcode.jback.core.registration.InitialRegistrationData;
import ch.patchcode.jback.core.registration.PendingRegistration;
import ch.patchcode.jback.core.registration.RegistrationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationServiceFake implements RegistrationService {

    @Override
    public PendingRegistration.Id beginRegistration(InitialRegistrationData data) {

        return PendingRegistration.Id.of(UUID.randomUUID());
    }

    @Override
    public ConfirmationResult concludeRegistration(UUID id, String verificationCode) {

        return ConfirmationResult.SUCCESS;
    }
}
