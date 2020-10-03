package ch.patchcode.jback.presentation.impl;

import ch.patchcode.jback.presentation.Authentication;
import ch.patchcode.jback.securityEntities.PendingRegistration;
import ch.patchcode.jback.securityEntities.VerificationMean;

public class TemporaryAuthentication
        extends ch.patchcode.jback.security.authentications.TemporaryAuthentication
        implements Authentication {


    public static TemporaryAuthentication of(PendingRegistration registration) {

        return new TemporaryAuthentication(
                registration.getFirstName(),
                registration.getLastName(),
                registration.getVerificationMean());
    }

    public TemporaryAuthentication(String firstName, String lastName, VerificationMean mean) {
        super(firstName, lastName, mean);
    }
}
