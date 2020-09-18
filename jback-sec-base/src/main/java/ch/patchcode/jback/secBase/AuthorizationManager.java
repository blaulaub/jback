package ch.patchcode.jback.secBase;

import ch.patchcode.jback.secBase.secModelImpl.Person;
import ch.patchcode.jback.secBase.secModelImpl.Principal;

public interface AuthorizationManager<
        TPerson extends Person<TVerificationMean>,
        TInitialRegistrationData extends InitialRegistrationData,
        TVerificationMean extends VerificationMean> {

    /**
     * Let any user register when submitting some data, providing him with a {@see PendingRegistration.Id}.
     * <p>
     * This will trigger some workflow that should eventually complete the {@see PendingRegistration}.
     *
     * @param initialRegistrationData with the basic, required details for a registration
     * @return the ID of the now pending registration
     */
    PendingRegistration.Id setupRegistration(TInitialRegistrationData initialRegistrationData);

    /**
     * Let any user conclude hist pending authentication.
     * <p>
     * This allows any user who started registration by calling {@see #setupRegistration} to prove his identity
     * via some verification code, and thus gain some additional privileges (usually the privilege to continue
     * or finish the next steps of the enrollment).
     *
     * @param registrationId   the ID of the currently pending registration
     * @param verificationCode the code confirming the registration
     */
    void authenticate(PendingRegistration.Id registrationId, VerificationCode verificationCode);

    /**
     * Make the person a client of the given principal.
     *
     * @param principal
     * @param person
     */
    void addClient(Principal<TVerificationMean> principal, TPerson person);
}
