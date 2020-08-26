package ch.patchcode.jback.core.registration;

import java.util.UUID;

public interface RegistrationService {

    PendingRegistration.Id process(InitialRegistrationData toDomain);

    ConfirmationResult confirm(UUID id, String verificationCode);
}
