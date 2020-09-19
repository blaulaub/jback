package ch.patchcode.jback.security.verificationCodes.impl;

import ch.patchcode.jback.security.verificationCodes.VerificationCodeProvider;

import javax.inject.Inject;
import java.util.Random;

public class FourDigitCodeProvider implements VerificationCodeProvider {

    private final Random rnd;

    public FourDigitCodeProvider(Random rnd) {
        this.rnd = rnd;
    }

    @Inject
    public String generateRandomCode() {

        return String.format("%04d", rnd.nextInt(10000));
    }
}
