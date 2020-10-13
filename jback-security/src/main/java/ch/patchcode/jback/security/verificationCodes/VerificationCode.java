package ch.patchcode.jback.security.verificationCodes;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface VerificationCode {

    String getVerificationCode();

    static VerificationCode of(String verificationCode) {

        return new Builder().setVerificationCode(verificationCode).build();
    }

    class Builder extends VerificationCode_Builder {
    }
}
