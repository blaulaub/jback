package ch.patchcode.jback.jpa.registration;

import ch.patchcode.jback.security.registration.PendingRegistration;
import ch.patchcode.jback.security.registration.VerificationMean;

public class ToRegistrationConverter implements VerificationMean.VerificationMeanVisitor<Registration> {

    public Registration convert(PendingRegistration pendingRegistration) {

        var result = pendingRegistration.getVerificationMean().accept(this);
        result.setFirstName(pendingRegistration.getFirstName());
        result.setLastName(pendingRegistration.getLastName());
        result.setVerificationCode(pendingRegistration.getVerificationCode());
        result.setExpiresAt(pendingRegistration.getExpiresAt().toEpochMilli());
        return result;
    }

    @Override
    public Registration visit(VerificationMean.VerificationByConsole verificationByConsole) {

        var result = new Registration.ConsoleRegistration();
        return result;
    }

    @Override
    public Registration visit(VerificationMean.VerificationByEmail verificationByEmail) {

        var result = new Registration.EmailRegistration();
        result.setEmail(verificationByEmail.getEmailAddress());
        return result;
    }

    @Override
    public Registration visit(VerificationMean.VerificationBySms verificationBySms) {

        var result = new Registration.SmsRegistration();
        result.setPhoneNumber(verificationBySms.getPhoneNumber());
        return result;
    }
}
