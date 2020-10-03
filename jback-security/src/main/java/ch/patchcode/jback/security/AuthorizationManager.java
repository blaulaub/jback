package ch.patchcode.jback.security;

import ch.patchcode.jback.core.entities.Person;
import ch.patchcode.jback.securityEntities.PersonalAuthentication;
import ch.patchcode.jback.securityEntities.Principal;
import ch.patchcode.jback.security.secBaseImpl.InitialRegistrationData;
import ch.patchcode.jback.securityEntities.PendingRegistration;
import ch.patchcode.jback.securityEntities.VerificationMean;

public interface AuthorizationManager extends ch.patchcode.jback.secBase.AuthorizationManager<
        Person,
        VerificationMean,
        Principal
        > {

    /**
     * Let any user register when submitting some data, providing him with a {@see PendingRegistration.Id}.
     * <p>
     * This will trigger some workflow that should eventually complete the {@see PendingRegistration}.
     *
     * @param initialRegistrationData with the basic, required details for a registration
     * @return the ID of the now pending registration
     */
    PendingRegistration.Id setupRegistration(InitialRegistrationData initialRegistrationData);

    // from ch.patchcode.jback.secBase.AuthorizationManager

    @Override
    void addClient(Principal principal, Person person);

    PersonalAuthentication createAuthorizationFor(Person person, Iterable<VerificationMean> means);

    TryLoginResult tryLogin(LoginData data);
}
