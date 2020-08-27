package ch.patchcode.jback.core.verificationCodes.impl;

import ch.patchcode.jback.core.verificationCodes.VerificationCodeProvider;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class FourDigitCodeProvider implements VerificationCodeProvider {

    private static final Random RND = new Random();

    @Override
    public String generateRandomCode() {

        return String.format("%04d", RND.nextInt(10000));
    }
}
