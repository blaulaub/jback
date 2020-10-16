package ch.patchcode.jback.presentation.impl;

import org.springframework.security.core.AuthenticationException;

import java.util.UUID;

public class NoPendingRegistrationException extends AuthenticationException {

    public NoPendingRegistrationException(UUID registrationId) {

        super("No registration found for " + registrationId);
    }
}
