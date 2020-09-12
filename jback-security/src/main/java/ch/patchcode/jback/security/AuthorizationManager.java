package ch.patchcode.jback.security;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.secBase.InitialRegistrationData;
import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationCode;
import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.security.authentications.PersonalAuthentication;

public interface AuthorizationManager extends ch.patchcode.jback.secBase.AuthorizationManager {

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

    PersonalAuthentication createAuthorizationFor(Person person, Iterable<VerificationMean> means);
}
