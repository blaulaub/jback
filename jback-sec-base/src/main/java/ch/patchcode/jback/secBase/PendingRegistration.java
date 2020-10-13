package ch.patchcode.jback.secBase;

import java.time.Instant;

public interface PendingRegistration {

    String getFirstName();

    String getLastName();

    VerificationMean.Draft getVerificationMean();

    String getVerificationCode();

    Instant getExpiresAt();
}
