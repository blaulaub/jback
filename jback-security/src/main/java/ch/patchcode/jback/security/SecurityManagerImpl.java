package ch.patchcode.jback.security;

import ch.patchcode.jback.secBase.SecurityManager;
import ch.patchcode.jback.secBase.*;
import ch.patchcode.jback.security.authentications.TemporaryAuthentication;
import ch.patchcode.jback.security.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityManagerImpl implements SecurityManager {

    private final RegistrationService registrationService;

    @Autowired
    public SecurityManagerImpl(RegistrationService registrationService) {

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
}
