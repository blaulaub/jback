package ch.patchcode.jback.sec.authentication;

import ch.patchcode.jback.sec.registration.ConfirmationResult;
import ch.patchcode.jback.sec.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class PendingVerificationAuthenticationManager implements AuthenticationManager, AuthenticationProvider {

    private RegistrationService registrationService;

    @Autowired
    public PendingVerificationAuthenticationManager(RegistrationService registrationService) {

        this.registrationService = registrationService;
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return PendingVerificationAuthentication.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (!(authentication instanceof PendingVerificationAuthentication)) {
            return null;
        }

        if (authentication.isAuthenticated()) {
            return authentication;
        }

        return authenticate((PendingVerificationAuthentication) authentication);
    }

    private Authentication authenticate(PendingVerificationAuthentication verificationAuthentication) {

        var result = registrationService.confirmRegistration(
                verificationAuthentication.getId(),
                verificationAuthentication.getCode()
        );

        if (result == ConfirmationResult.CONFIRMED) {
            // future: if there is a principal with the same verification mean (email, mobile), return an
            // authorization for that principal

            // otherwise, return a temporary principal for the given authorization
            return PendingVerificationAuthentication.Confirmed.of(
                    verificationAuthentication.getId(),
                    verificationAuthentication.getCode()
            );
        }

        if (result == ConfirmationResult.MISMATCH) {
            throw new PendingVerificationMismatchException();
        }

        throw new PendingVerificationNotFoundException();
    }

    public static class PendingVerificationMismatchException extends AuthenticationException {

        public PendingVerificationMismatchException() {

            super("mismatch");
        }
    }

    public static class PendingVerificationNotFoundException extends AuthenticationException {

        public PendingVerificationNotFoundException() {

            super("not found");
        }
    }
}
