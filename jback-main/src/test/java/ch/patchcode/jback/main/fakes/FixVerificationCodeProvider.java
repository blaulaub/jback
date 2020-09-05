package ch.patchcode.jback.main.fakes;

import ch.patchcode.jback.security.verificationCodes.VerificationCodeProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class FixVerificationCodeProvider implements VerificationCodeProvider {

    public final static String FIX_VERIFICATION_CODE = "782174";

    @Override
    public String generateRandomCode() {
        return FIX_VERIFICATION_CODE;
    }
}
