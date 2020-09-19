package ch.patchcode.jback.security;

import ch.patchcode.jback.security.verificationCodes.VerificationCodeProvider;
import ch.patchcode.jback.security.verificationCodes.impl.FourDigitCodeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

/**
 * Spring configuration that automatically contains all services (and other
 * components) of this package and below through component scan. It does not
 * pre-configure any dependencies (repositories), these need to be configured
 * elsewhere.
 */
@Configuration
@ComponentScan
public class SecurityConfiguration {

    private static final Random RND = new Random();

    @Bean
    public VerificationCodeProvider getVerificationCodeProvider() {

        return new FourDigitCodeProvider(RND);
    }

}
