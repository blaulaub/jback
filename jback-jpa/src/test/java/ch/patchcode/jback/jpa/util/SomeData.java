package ch.patchcode.jback.jpa.util;

import ch.patchcode.jback.jpa.registration.Registration;
import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationMean;

import java.time.Duration;
import java.time.Instant;

public class SomeData {

    public static PendingRegistration somePendingRegistration() {

        return somePendingRegistration(new VerificationMean.VerificationByConsole());
    }

    public static PendingRegistration somePendingRegistration(VerificationMean verificationMean) {

        return new PendingRegistration.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setExpiresAt(Instant.now().plus(Duration.ofMinutes(5)))
                .setVerificationCode("1234")
                .setVerificationMean(verificationMean)
                .build();
    }

    public static Registration someRegistration() {
        
        Registration registration = new Registration.ConsoleRegistration();
        registration.setFirstName("Tom");
        registration.setLastName("Sawyer");
        registration.setVerificationCode("1234");
        registration.setExpiresAt(Instant.now().toEpochMilli());
        return registration;
    }
}
