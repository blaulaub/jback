package ch.patchcode.jback.core.registration;

import java.util.UUID;

/**
 * Registration is the first step where the system learns about the user.
 * <p>
 * In an initial step {@link #setupRegistration(InitialRegistrationData)} user supplies
 * some basic data about himself, usually including some mean of callback.
 * <p>
 * Then, using a communication channel that is likely unique to the user, the system
 * provides the user with some secret code that has to be returned in a second step
 * {@link #confirmRegistration(UUID, String)}.
 */
public interface RegistrationService {

    /**
     * Start a new registration with basic registration parameters.
     *
     * @param data basic registration parameters
     * @return id of the new registration
     */
    PendingRegistration.Id setupRegistration(InitialRegistrationData data);

    /**
     * Confirm a pending registration with the given verification code.
     * <p>
     * A positive result will will <b>not</b> change the pending registration,
     * reusing the code and thus getting another positive confirmation will
     * still be possible. Another call to {@link #removeRegistration(UUID)} is
     * required to avoid unwanted reuse.
     *
     * @param id               of the pending registration
     * @param verificationCode verification code confirming the registration
     * @return result (success, not found, wrong code)
     */
    ConfirmationResult confirmRegistration(UUID id, String verificationCode);

    /**
     * Remove a pending registration.
     * <p>
     * This should be called instantly after a pending registration was confirmed,
     * to avoid reuse (and abuse).
     *
     * @param id of the pending registration
     */
    void removeRegistration(UUID id);
}
