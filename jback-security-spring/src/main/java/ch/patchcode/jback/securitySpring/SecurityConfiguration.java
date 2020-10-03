package ch.patchcode.jback.securitySpring;

import ch.patchcode.jback.security.AuthorizationManager;
import ch.patchcode.jback.security.entities.PersonalAuthenticationRepository;
import ch.patchcode.jback.security.authentications.PersonalAuthenticationService;
import ch.patchcode.jback.security.authentications.impl.PersonalAuthenticationServiceImpl;
import ch.patchcode.jback.security.impl.AuthorizationManagerImpl;
import ch.patchcode.jback.security.entities.PendingRegistrationRepository;
import ch.patchcode.jback.security.registration.RegistrationService;
import ch.patchcode.jback.security.registration.VerificationService;
import ch.patchcode.jback.security.registration.impl.ConsoleVerificationServiceImpl;
import ch.patchcode.jback.security.registration.impl.RegistrationServiceImpl;
import ch.patchcode.jback.security.verificationCodes.VerificationCodeProvider;
import ch.patchcode.jback.security.verificationCodes.impl.FourDigitCodeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class SecurityConfiguration {

    private static final Random RND = new Random();

    @Bean
    public AuthorizationManager getAuthorizationManager(
            RegistrationService registrationService,
            PersonalAuthenticationService personalAuthenticationService,
            PersonalAuthenticationRepository personalAuthenticationRepository
    ) {

        return new AuthorizationManagerImpl(
                registrationService,
                personalAuthenticationService,
                personalAuthenticationRepository
        );
    }

    @Bean
    public PersonalAuthenticationService getPersonalAuthenticationService(
            PersonalAuthenticationRepository personalAuthenticationRepository
    ) {

        return new PersonalAuthenticationServiceImpl(personalAuthenticationRepository);
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
