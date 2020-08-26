package ch.patchcode.jback.jpa.registration;

import ch.patchcode.jback.core.registration.PendingRegistration;
import ch.patchcode.jback.core.registration.VerificationMean;

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

        var result = new ConsoleRegistration();
        return result;
    }

    @Override
    public Registration visit(VerificationMean.VerificationByEmail verificationByEmail) {

        var result = new EmailRegistration();
        result.setEmail(verificationByEmail.getEmailAddress());
        return result;
    }

    @Override
    public Registration visit(VerificationMean.VerificationBySms verificationBySms) {

        var result = new SmsRegistration();
        result.setPhoneNumber(verificationBySms.getPhoneNumber());
        return result;
    }
}
