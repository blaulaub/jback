package ch.patchcode.jback.security;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.secBase.InitialRegistrationData;
import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationCode;

public interface AuthorizationManager extends ch.patchcode.jback.secBase.AuthorizationManager {

    /**
     * Returns an {@see Authentication} that is equal to the given {@see Authentication},
     * or upgraded in case given {@see Person} permits that.
     *
     * @param callerAuth is the current authentication from security perspective
     * @param person is the current person from the domain perspective
     * @return some equal or upgraded authentication
     */
    Authentication tryGetUpgrade(Authentication callerAuth, Person person);

    // from ch.patchcode.jback.secBase.AuthorizationManager

    /**
     * {@inheritDoc}
     */
    @Override
    PendingRegistration.Id setupRegistration(InitialRegistrationData initialRegistrationData);

    /**
     * {@inheritDoc}
     */
    @Override
    void authenticate(PendingRegistration.Id registrationId, VerificationCode verificationCode);
}
