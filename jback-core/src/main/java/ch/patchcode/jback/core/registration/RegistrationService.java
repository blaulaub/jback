package ch.patchcode.jback.core.registration;

import java.util.UUID;

public interface RegistrationService {

    /**
     * Start a new registration by supplying basic registration parameters.
     *
     * @param data basic registration parameters
     * @return id of the new registration
     */
    PendingRegistration.Id beginRegistration(InitialRegistrationData data);

    /**
     * Conclude a pending registration by supplying the verification code.
     *
     * @param id               of the pending registration
     * @param verificationCode verification code confirming the registration
     * @return result (success, not found, wrong code)
     */
    ConfirmationResult concludeRegistration(UUID id, String verificationCode);
}
