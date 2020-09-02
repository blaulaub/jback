package ch.patchcode.jback.security.verificationCodes;

public interface VerificationCodeProvider {

    String generateRandomCode();
}
