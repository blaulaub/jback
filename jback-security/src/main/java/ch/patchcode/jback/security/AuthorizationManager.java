package ch.patchcode.jback.security;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.secBase.InitialRegistrationData;
import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationCode;
import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.secBase.secModelImpl.Principal;
import ch.patchcode.jback.security.authentications.PersonalAuthentication;

public interface AuthorizationManager extends ch.patchcode.jback.secBase.AuthorizationManager<Person> {

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

    @Override
    void addClient(Principal principal, Person person);

    PersonalAuthentication createAuthorizationFor(Person person, Iterable<VerificationMean> means);
}
