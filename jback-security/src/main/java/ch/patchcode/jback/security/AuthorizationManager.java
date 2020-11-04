package ch.patchcode.jback.security;

import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.coreRepositories.NotAllowedException;
import ch.patchcode.jback.securityEntities.authentications.PersonalAuthentication;
import ch.patchcode.jback.securityEntities.authentications.Principal;
import ch.patchcode.jback.security.registration.InitialRegistrationData;
import ch.patchcode.jback.securityEntities.verificationMeans.VerificationMean;

import java.util.UUID;

public interface AuthorizationManager {

    /**
     * Let any user register when submitting some data, providing him with a {@see PendingRegistration.Id}.
     * <p>
     * This will trigger some workflow that should eventually complete the {@see PendingRegistration}.
     *
     * @param initialRegistrationData with the basic, required details for a registration
     * @return the ID of the now pending registration
     */
    UUID setupRegistration(InitialRegistrationData initialRegistrationData);

    /**
     * Adds the given {@link Person} to the {@link Principal}'s persons.
     */
    Principal addPersonToPrincipal(Principal principal, Person person) throws NotAllowedException;

    PersonalAuthentication createAuthorizationFor(Person person, Iterable<VerificationMean.Draft> means);

    /**
     * @return some {@link Principal}, or {@code null}
     */
    Principal tryLogin(LoginData data);
}
