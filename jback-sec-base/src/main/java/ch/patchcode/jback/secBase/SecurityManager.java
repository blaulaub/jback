package ch.patchcode.jback.secBase;

import java.util.UUID;

public interface SecurityManager {

    PendingRegistration.Id setupRegistration(InitialRegistrationData initialRegistrationData);

    ConfirmationResult confirmRegistration(UUID id, String verificationCode);
}
