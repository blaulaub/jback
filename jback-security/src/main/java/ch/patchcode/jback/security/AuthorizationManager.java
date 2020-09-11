package ch.patchcode.jback.security;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.secBase.InitialRegistrationData;
import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationCode;

public interface AuthorizationManager extends ch.patchcode.jback.secBase.AuthorizationManager {

    Authentication tryUpgrade(Authentication callerAuth, Person person);

    // from ch.patchcode.jback.secBase.AuthorizationManager

    @Override
    PendingRegistration.Id setupRegistration(InitialRegistrationData initialRegistrationData);

    @Override
    void authenticate(PendingRegistration.Id registrationId, VerificationCode verificationCode);
}
