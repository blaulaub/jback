package ch.patchcode.jback.main;

import ch.patchcode.jback.security.registration.VerificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakesConfiguration {

    @Bean
    VerificationService.EmailVerificationService getEmailVerificationService(VerificationService.ConsoleVerificationService fillIn) {

        return fillIn::sendOut;
    }

    @Bean
    VerificationService.SmsVerificationService getSmsVerificationService(VerificationService.ConsoleVerificationService fillIn) {

        return fillIn::sendOut;
    }
}
