package ch.patchcode.jback.presentation;

import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.presentation.impl.PersonalAuthentication;
import ch.patchcode.jback.securityEntities.Principal;
import ch.patchcode.jback.security.secBaseImpl.InitialRegistrationData;
import ch.patchcode.jback.security.secBaseImpl.VerificationCode;
import ch.patchcode.jback.securityEntities.VerificationMean;

import java.util.UUID;

public interface AuthorizationManager {

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
    void authenticate(UUID registrationId, VerificationCode verificationCode);

    /**
     * Let any user register when submitting some data, providing him with a {@see PendingRegistration.Id}.
     * <p>
     * This will trigger some workflow that should eventually complete the {@see PendingRegistration}.
     *
     * @param initialRegistrationData with the basic, required details for a registration
     * @return the ID of the now pending registration
     */
    UUID setupRegistration(InitialRegistrationData initialRegistrationData);

    PersonalAuthentication createAuthorizationFor(Person person, Iterable<VerificationMean.Draft> means);

    void addClient(Principal principal, Person person);

    TryLoginResult tryLogin(LoginData data);

}