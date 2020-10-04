package ch.patchcode.jback.api;

import ch.patchcode.jback.security.verificationCodes.VerificationCodeProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class ConstantVerificationCodeProvider implements VerificationCodeProvider {

    public static final String VERIFICATION_CODE = "1234";

    @Override
    public String generateRandomCode() {
        return VERIFICATION_CODE;
    }
}
