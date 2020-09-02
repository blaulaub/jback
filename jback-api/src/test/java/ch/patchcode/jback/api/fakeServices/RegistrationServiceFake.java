package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.security.registration.ConfirmationResult;
import ch.patchcode.jback.security.registration.InitialRegistrationData;
import ch.patchcode.jback.security.registration.PendingRegistration;
import ch.patchcode.jback.security.registration.RegistrationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationServiceFake implements RegistrationService {

    @Override
    public PendingRegistration.Id setupRegistration(InitialRegistrationData data) {

        return PendingRegistration.Id.of(UUID.randomUUID());
    }

    @Override
    public ConfirmationResult confirmRegistration(UUID id, String verificationCode) {

        return ConfirmationResult.CONFIRMED;
    }

    @Override
    public void removeRegistration(UUID id) {
        // do nothing
    }
}
