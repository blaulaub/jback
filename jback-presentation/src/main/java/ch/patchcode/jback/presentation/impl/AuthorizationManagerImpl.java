package ch.patchcode.jback.presentation.impl;

import ch.patchcode.jback.core.entities.Person;
import ch.patchcode.jback.presentation.AuthorizationManager;
import ch.patchcode.jback.presentation.LoginData;
import ch.patchcode.jback.presentation.TryLoginResult;
import ch.patchcode.jback.security.entities.Principal;
import ch.patchcode.jback.security.registration.ConfirmationResult;
import ch.patchcode.jback.security.registration.RegistrationService;
import ch.patchcode.jback.security.secBaseImpl.InitialRegistrationData;
import ch.patchcode.jback.security.entities.PendingRegistration;
import ch.patchcode.jback.security.secBaseImpl.VerificationCode;
import ch.patchcode.jback.security.entities.VerificationMean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("presentation.authorizationManager")
public class AuthorizationManagerImpl implements AuthorizationManager {

    private final static Logger LOG = LoggerFactory.getLogger(ch.patchcode.jback.security.impl.AuthorizationManagerImpl.class);

    private final ch.patchcode.jback.security.AuthorizationManager authorizationManager;
    private final RegistrationService registrationService;

    @Autowired
    public AuthorizationManagerImpl(
            ch.patchcode.jback.security.AuthorizationManager authorizationManager,
            RegistrationService registrationService
    ) {
        this.authorizationManager = authorizationManager;
        this.registrationService = registrationService;
    }

    @Override
    public void authenticate(PendingRegistration.Id registrationId, VerificationCode verificationCode) {

        var pendingRegistration = registrationService.getRegistration(registrationId.getId())
                .orElseThrow(() -> new NoPendingRegistrationException(registrationId));

        var confirmationResult = registrationService.confirmRegistration(
                pendingRegistration,
                verificationCode.getVerificationCode()
        );

        confirmationResult.accept(new ConfirmationResult.Visitor() {
            @Override
            public void caseConfirmed() {

                LOG.debug("change security context to TemporaryAuthentication");
                SecurityContextHolder.getContext()
                        .setAuthentication(TemporaryAuthentication.of(pendingRegistration));
            }

            @Override
            public void caseNotFound() {
                throw new NoPendingRegistrationException(registrationId);
            }

            @Override
            public void caseMismatch() {
                throw new BadCredentialsException("Invalid code provided for " + registrationId.getId());
            }
        });
    }

    @Override
    public PendingRegistration.Id setupRegistration(InitialRegistrationData initialRegistrationData) {

        return authorizationManager.setupRegistration(initialRegistrationData);
    }

    @Override
    public PersonalAuthentication createAuthorizationFor(Person person, Iterable<VerificationMean> means) {

        return PersonalAuthentication.fromDomain(authorizationManager.createAuthorizationFor(person, means));
    }

    @Override
    public void addClient(Principal principal, Person person) {

        authorizationManager.addClient(principal, person);
    }

    @Override
    public TryLoginResult tryLogin(LoginData data) {

        // maybe we can just delegate it further...
        authorizationManager.tryLogin(data.toDomain());

        // TODO implement
        throw new RuntimeException("not implemented");
    }
}
