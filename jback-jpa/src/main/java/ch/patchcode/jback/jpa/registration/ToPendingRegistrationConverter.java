package ch.patchcode.jback.jpa.registration;

import ch.patchcode.jback.core.registration.PendingRegistration;
import ch.patchcode.jback.core.registration.VerificationMean;

import java.time.Instant;

public class ToPendingRegistrationConverter implements Registration.RegistrationVisitor<PendingRegistration.Builder> {

    public PendingRegistration convert(Registration registration) {

        var builder = registration.accept(this);
        builder.setFirstName(registration.getFirstName());
        builder.setLastName(registration.getLastName());
        builder.setVerificationCode(registration.getVerificationCode());
        builder.setExpiresAt(Instant.ofEpochMilli(registration.getExpiresAt()));
        return builder.build();
    }

    @Override
    public PendingRegistration.Builder visit(Registration.ConsoleRegistration registrationByConsole) {

        return new PendingRegistration.Builder()
                .setVerificationMean(new VerificationMean.VerificationByConsole());
    }

    @Override
    public PendingRegistration.Builder visit(Registration.EmailRegistration registrationByEmail) {

        return new PendingRegistration.Builder()
                .setVerificationMean(new VerificationMean.VerificationByEmail.Builder()
                        .setEmailAddress(registrationByEmail.getEmail())
                        .build());
    }

    @Override
    public PendingRegistration.Builder visit(Registration.SmsRegistration registrationBySms) {

        return new PendingRegistration.Builder()
                .setVerificationMean(new VerificationMean.VerificationBySms.Builder()
                        .setPhoneNumber(registrationBySms.getPhoneNumber())
                        .build());
    }
}
