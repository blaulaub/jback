package ch.patchcode.jback.security;

import ch.patchcode.jback.security.authentications.PersonalAuthenticationRepository;
import ch.patchcode.jback.security.impl.AuthorizationManagerImpl;
import ch.patchcode.jback.security.registration.PendingRegistrationRepository;
import ch.patchcode.jback.security.registration.RegistrationService;
import ch.patchcode.jback.security.registration.VerificationService;
import ch.patchcode.jback.security.registration.impl.ConsoleVerificationServiceImpl;
import ch.patchcode.jback.security.registration.impl.RegistrationServiceImpl;
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
    public AuthorizationManager getAuthorizationManager(
            RegistrationService registrationService,
            PersonalAuthenticationRepository personalAuthenticationRepository
    ) {

        return new AuthorizationManagerImpl(
                registrationService,
                personalAuthenticationRepository
        );
    }

    @Bean
    public RegistrationService getRegistrationService(
            VerificationService.ConsoleVerificationService consoleVerificationService,
            VerificationService.EmailVerificationService emailVerificationService,
            VerificationService.SmsVerificationService smsVerificationService,
            PendingRegistrationRepository pendingRegistrationRepository,
            VerificationCodeProvider verificationCodeProvider
    ) {

        return new RegistrationServiceImpl(consoleVerificationService,
                emailVerificationService,
                smsVerificationService,
                pendingRegistrationRepository,
                verificationCodeProvider);
    }

    @Bean
    public VerificationService.ConsoleVerificationService getConsoleVerificationService() {

        return new ConsoleVerificationServiceImpl();
    }

    @Bean
    public VerificationCodeProvider getVerificationCodeProvider() {

        return new FourDigitCodeProvider(RND);
    }
}
