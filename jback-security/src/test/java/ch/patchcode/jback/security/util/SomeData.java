package ch.patchcode.jback.security.util;

import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationMean;

import java.time.Duration;
import java.time.Instant;

public class SomeData {

    public static PendingRegistration somePendingRegistration() {
        return new PendingRegistration.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationCode("1234")
                .setExpiresAt(Instant.now().plus(Duration.ofMinutes(30)))
                .setVerificationMean(new VerificationMean.VerificationByConsole())
                .build();
    }
}
