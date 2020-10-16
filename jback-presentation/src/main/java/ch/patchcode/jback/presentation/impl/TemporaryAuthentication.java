package ch.patchcode.jback.presentation.impl;

import ch.patchcode.jback.securityEntities.VerificationMean;

public class TemporaryAuthentication
        extends SpringAuthentication<ch.patchcode.jback.securityEntities.TemporaryAuthentication> {

    public static TemporaryAuthentication of(String firstName, String lastName, VerificationMean.Draft verificationMean) {

        return new TemporaryAuthentication(
                firstName,
                lastName,
                verificationMean);
    }

    public TemporaryAuthentication(String firstName, String lastName, VerificationMean.Draft mean) {

        super(new ch.patchcode.jback.securityEntities.TemporaryAuthentication(firstName, lastName, mean));
    }
}
