package ch.patchcode.jback.security.util;

import ch.patchcode.jback.security.entities.PendingRegistration;
import ch.patchcode.jback.security.entities.VerificationMean;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class SomeData {

    public static PendingRegistration somePendingRegistration() {
        return new PendingRegistration.Builder()
                .setId(PendingRegistration.Id.of(UUID.randomUUID()))
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationCode("1234")
                .setExpiresAt(Instant.now().plus(Duration.ofMinutes(30)))
                .setVerificationMean(new VerificationMean.VerificationByConsole())
                .build();
    }
}
