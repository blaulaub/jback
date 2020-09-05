package ch.patchcode.jback.security;

import ch.patchcode.jback.secBase.ConfirmationResult;
import ch.patchcode.jback.secBase.InitialRegistrationData;
import ch.patchcode.jback.secBase.SecurityManager;
import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.security.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
    public ConfirmationResult confirmRegistration(UUID id, String verificationCode) {

        ConfirmationResult confirmationResult = registrationService.confirmRegistration(id, verificationCode);

        if (confirmationResult == ConfirmationResult.CONFIRMED) {
            // TODO: change security context, add authentication
            // SecurityContextHolder.getContext().setAuthentication(auth);
        }

        return confirmationResult;
    }
}
