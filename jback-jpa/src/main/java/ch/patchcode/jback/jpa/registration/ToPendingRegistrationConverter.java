package ch.patchcode.jback.jpa.registration;

import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationMean;

import java.time.Instant;

public class ToPendingRegistrationConverter implements RegistrationJpa.Visitor<PendingRegistration.Builder> {

    public PendingRegistration convert(RegistrationJpa registration) {

        var builder = registration.accept(this);
        builder.setFirstName(registration.getFirstName());
        builder.setLastName(registration.getLastName());
        builder.setVerificationCode(registration.getVerificationCode());
        builder.setExpiresAt(Instant.ofEpochMilli(registration.getExpiresAt()));
        return builder.build();
    }

    @Override
    public PendingRegistration.Builder visit(RegistrationJpa.ConsoleRegistrationJpa registrationByConsole) {

        return new PendingRegistration.Builder()
                .setVerificationMean(new VerificationMean.VerificationByConsole());
    }

    @Override
    public PendingRegistration.Builder visit(RegistrationJpa.EmailRegistrationJpa registrationByEmail) {

        return new PendingRegistration.Builder()
                .setVerificationMean(new VerificationMean.VerificationByEmail.Builder()
                        .setEmailAddress(registrationByEmail.getEmail())
                        .build());
    }

    @Override
    public PendingRegistration.Builder visit(RegistrationJpa.SmsRegistrationJpa registrationBySms) {

        return new PendingRegistration.Builder()
                .setVerificationMean(new VerificationMean.VerificationBySms.Builder()
                        .setPhoneNumber(registrationBySms.getPhoneNumber())
                        .build());
    }
}
