package ch.patchcode.jback.presentation.impl;

import ch.patchcode.jback.security.entities.PendingRegistration;
import org.springframework.security.core.AuthenticationException;

public class NoPendingRegistrationException extends AuthenticationException {

    public NoPendingRegistrationException(PendingRegistration.Id registrationId) {

        super("No registration found for " + registrationId.getId());
    }
}
