package ch.patchcode.jback.presentation;

import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.coreEntities.roles.Role;
import ch.patchcode.jback.coreRepositories.NotAllowedException;
import ch.patchcode.jback.presentation.impl.SpringAuthentication;
import ch.patchcode.jback.security.registration.InitialRegistrationData;
import ch.patchcode.jback.security.verificationCodes.VerificationCode;
import ch.patchcode.jback.securityEntities.authentications.Principal;
import ch.patchcode.jback.securityEntities.verificationMeans.VerificationMean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthenticationManager {

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

    SpringAuthentication<?> createAuthorizationFor(Person person, Iterable<VerificationMean.Draft> means);

    SpringAuthentication<?> addPersonToPrincipal(Principal principal, Person person) throws NotAllowedException;

    TryLoginResult tryLogin(LoginData data);

    List<Role> getAvailableRoles(Principal principal);

    Optional<Role> getCurrentRole();

    void setCurrentRole(Role toDomain) throws NotAllowedException;
}
