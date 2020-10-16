package ch.patchcode.jback.presentation.impl;

import ch.patchcode.jback.presentation.Authentication;
import ch.patchcode.jback.securityEntities.VerificationMean;

public class TemporaryAuthentication
        extends ch.patchcode.jback.securityEntities.TemporaryAuthentication
        implements Authentication {

    public static TemporaryAuthentication of(String firstName, String lastName, VerificationMean.Draft verificationMean) {
        return new TemporaryAuthentication(
                firstName,
                lastName,
                verificationMean);
    }

    public TemporaryAuthentication(String firstName, String lastName, VerificationMean.Draft mean) {
        super(firstName, lastName, mean);
    }
}
