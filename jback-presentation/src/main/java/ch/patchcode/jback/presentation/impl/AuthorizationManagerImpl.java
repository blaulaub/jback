package ch.patchcode.jback.presentation.impl;

import ch.patchcode.jback.core.RoleService;
import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.coreEntities.roles.Role;
import ch.patchcode.jback.presentation.AuthorizationManager;
import ch.patchcode.jback.presentation.LoginData;
import ch.patchcode.jback.presentation.TryLoginResult;
import ch.patchcode.jback.security.registration.ConfirmationResult;
import ch.patchcode.jback.security.registration.InitialRegistrationData;
import ch.patchcode.jback.security.registration.RegistrationService;
import ch.patchcode.jback.security.verificationCodes.VerificationCode;
import ch.patchcode.jback.securityEntities.authentications.PersonalAuthentication;
import ch.patchcode.jback.securityEntities.authentications.Principal;
import ch.patchcode.jback.securityEntities.authentications.TemporaryAuthentication;
import ch.patchcode.jback.securityEntities.verificationMeans.VerificationMean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("presentation.authorizationManager")
public class AuthorizationManagerImpl implements AuthorizationManager {

    private final static Logger LOG = LoggerFactory.getLogger(ch.patchcode.jback.security.impl.AuthorizationManagerImpl.class);

    private final ch.patchcode.jback.security.AuthorizationManager authorizationManager;
    private final RegistrationService registrationService;
    private final RoleService roleService;

    @Autowired
    public AuthorizationManagerImpl(
            ch.patchcode.jback.security.AuthorizationManager authorizationManager,
            RegistrationService registrationService,
            RoleService roleService) {

        this.authorizationManager = authorizationManager;
        this.registrationService = registrationService;
        this.roleService = roleService;
    }

    @Override
    public void authenticate(UUID registrationId, VerificationCode verificationCode) {

        var pendingRegistration = registrationService.getRegistration(registrationId)
                .orElseThrow(() -> new NoPendingRegistrationException(registrationId));

        var confirmationResult = registrationService.confirmRegistration(
                pendingRegistration,
                verificationCode.getVerificationCode()
        );

        confirmationResult.accept(new ConfirmationResult.Visitor() {
            @Override
            public void caseConfirmed() {

                LOG.debug("change security context to TemporaryAuthentication");

                String firstName = pendingRegistration.getFirstName();
                String lastName = pendingRegistration.getLastName();
                VerificationMean.Draft verificationMean = pendingRegistration.getVerificationMean();
                var authentication = SpringAuthentication.of(TemporaryAuthentication.of(firstName, lastName, verificationMean));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            @Override
            public void caseNotFound() {
                throw new NoPendingRegistrationException(registrationId);
            }

            @Override
            public void caseMismatch() {
                throw new BadCredentialsException("Invalid code provided for " + registrationId);
            }
        });
    }

    @Override
    public UUID setupRegistration(InitialRegistrationData initialRegistrationData) {

        return authorizationManager.setupRegistration(initialRegistrationData);
    }

    @Override
    public SpringAuthentication<PersonalAuthentication> createAuthorizationFor(Person person, Iterable<VerificationMean.Draft> means) {

        return SpringAuthentication.of(authorizationManager.createAuthorizationFor(person, means));
    }

    @Override
    public void addClient(Principal principal, Person person) {

        authorizationManager.addClient(principal, person);
    }

    @Override
    public TryLoginResult tryLogin(LoginData data) {

        var auth = authorizationManager.tryLogin(data.toDomain());

        if (auth == null) {

            return TryLoginResult.UNKNOWN_USER;
        }

        SecurityContextHolder.getContext().setAuthentication(SpringAuthentication.of(auth));
        return TryLoginResult.SUCCESS;
    }

    @Override
    public List<Role> getAvailableRoles(Principal principal) {

        return roleService.getRolesFor(principal.getPersons());
    }

    @Override
    public Optional<Role> getCurrentRole() {

        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(it -> it instanceof SpringAuthentication)
                .<SpringAuthentication<?>>map(it -> (SpringAuthentication<?>) it)
                .flatMap(SpringAuthentication::getRole);
    }
}
