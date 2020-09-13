package ch.patchcode.jback.jpa.registration;

import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationMean;

public class ToRegistrationConverter implements VerificationMean.VerificationMeanVisitor<RegistrationJpa> {

    public RegistrationJpa convert(PendingRegistration pendingRegistration) {

        var result = pendingRegistration.getVerificationMean().accept(this);
        result.setFirstName(pendingRegistration.getFirstName());
        result.setLastName(pendingRegistration.getLastName());
        result.setVerificationCode(pendingRegistration.getVerificationCode());
        result.setExpiresAt(pendingRegistration.getExpiresAt().toEpochMilli());
        return result;
    }

    @Override
    public RegistrationJpa visit(VerificationMean.VerificationByConsole verificationByConsole) {

        var result = new RegistrationJpa.ConsoleRegistrationJpa();
        return result;
    }

    @Override
    public RegistrationJpa visit(VerificationMean.VerificationByEmail verificationByEmail) {

        var result = new RegistrationJpa.EmailRegistrationJpa();
        result.setEmail(verificationByEmail.getEmailAddress());
        return result;
    }

    @Override
    public RegistrationJpa visit(VerificationMean.VerificationBySms verificationBySms) {

        var result = new RegistrationJpa.SmsRegistrationJpa();
        result.setPhoneNumber(verificationBySms.getPhoneNumber());
        return result;
    }
}
