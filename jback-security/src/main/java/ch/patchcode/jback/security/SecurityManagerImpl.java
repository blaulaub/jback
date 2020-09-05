package ch.patchcode.jback.security;

import ch.patchcode.jback.secBase.InitialRegistrationData;
import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.SecurityManager;
import ch.patchcode.jback.secBase.VerificationCode;
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

        // TODO use visitor
        switch (confirmationResult) {
            case CONFIRMED:
                SecurityContextHolder.getContext()
                        .setAuthentication(new TemporaryAuthentication(registrationId, pendingRegistration));
                break;
            case MISMATCH:
                throw new BadCredentialsException("Invalid code provided for " + registrationId.getId());
            case NOT_FOUND:
                throw new NoPendingRegistrationException(registrationId);
        }
    }
}
