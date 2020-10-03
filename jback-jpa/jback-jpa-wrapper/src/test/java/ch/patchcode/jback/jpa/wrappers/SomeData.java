package ch.patchcode.jback.jpa.wrappers;

import ch.patchcode.jback.securityEntities.PendingRegistration;
import ch.patchcode.jback.securityEntities.VerificationByConsole;
import ch.patchcode.jback.securityEntities.VerificationMean;

import java.time.Duration;
import java.time.Instant;

public class SomeData {

    public static PendingRegistration.Draft somePendingRegistrationDraft() {

        return somePendingRegistrationDraft(new VerificationByConsole());
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
