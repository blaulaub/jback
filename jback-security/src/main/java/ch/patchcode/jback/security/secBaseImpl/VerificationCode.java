package ch.patchcode.jback.security.secBaseImpl;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface VerificationCode extends ch.patchcode.jback.secBase.VerificationCode {

    @Override
    String getVerificationCode();

    static VerificationCode of(String verificationCode) {

        return new Builder().setVerificationCode(verificationCode).build();
    }

    class Builder extends VerificationCode_Builder {
    }
}
