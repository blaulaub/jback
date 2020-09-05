package ch.patchcode.jback.security;

import ch.patchcode.jback.secBase.PendingRegistration;
import org.springframework.security.core.AuthenticationException;

public class NoPendingRegistrationException extends AuthenticationException {

    public NoPendingRegistrationException(PendingRegistration.Id registrationId) {

        super("No registration found for " + registrationId.getId());
    }
}