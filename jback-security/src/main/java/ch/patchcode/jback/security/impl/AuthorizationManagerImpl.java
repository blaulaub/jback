package ch.patchcode.jback.security.impl;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.secBase.ConfirmationResult;
import ch.patchcode.jback.secBase.InitialRegistrationData;
import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationCode;
import ch.patchcode.jback.secBase.secModelImpl.Principal;
import ch.patchcode.jback.security.Authentication;
import ch.patchcode.jback.security.AuthorizationManager;
import ch.patchcode.jback.security.NoPendingRegistrationException;
import ch.patchcode.jback.security.authentications.TemporaryAuthentication;
import ch.patchcode.jback.security.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationManagerImpl implements AuthorizationManager {

    private final RegistrationService registrationService;

    @Autowired
    public AuthorizationManagerImpl(RegistrationService registrationService) {

        this.registrationService = registrationService;
    }

    @Override
    public PendingRegistration.Id setupRegistration(InitialRegistrationData initialRegistrationData) {

        return registrationService.setupRegistration(initialRegistrationData);
    }

    @Override
    public void authenticate(PendingRegistration.Id registrationId, VerificationCode verificationCode) {

        var pendingRegistration = registrationService.getRegistration(registrationId.getId())
                .orElseThrow(() -> new NoPendingRegistrationException(registrationId));

        var confirmationResult = registrationService.confirmRegistration(
                pendingRegistration,
                verificationCode.getVerificationCode()
        );

        confirmationResult.accept(new ConfirmationResult.Visitor<Void>() {
            @Override
            public Void caseConfirmed() {
                SecurityContextHolder.getContext()
                        .setAuthentication(new TemporaryAuthentication(pendingRegistration));
                return null;
            }

            @Override
            public Void caseNotFound() {
                throw new NoPendingRegistrationException(registrationId);
            }

            @Override
            public Void caseMismatch() {
                throw new BadCredentialsException("Invalid code provided for " + registrationId.getId());
            }
        });
    }

    @Override
    public Authentication tryUpgrade(Authentication callerAuth, Person person) {

        // TODO simple default: return input unmodified
        return callerAuth;
    }
}
