package ch.patchcode.jback.sec.verificationCodes;

public interface VerificationCodeProvider {

    String generateRandomCode();
}
