package ch.patchcode.jback.jpa.wrappers;

import ch.patchcode.jback.security.entities.PendingRegistration;
import ch.patchcode.jback.security.entities.VerificationMean;

import java.time.Duration;
import java.time.Instant;

public class SomeData {

    public static PendingRegistration.Draft somePendingRegistrationDraft() {

        return somePendingRegistrationDraft(new VerificationMean.VerificationByConsole());
    }

    public static PendingRegistration.Draft somePendingRegistrationDraft(VerificationMean verificationMean) {

        return new PendingRegistration.Draft.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setExpiresAt(Instant.now().plus(Duration.ofMinutes(5)))
                .setVerificationCode("1234")
                .setVerificationMean(verificationMean)
                .build();
    }
}
