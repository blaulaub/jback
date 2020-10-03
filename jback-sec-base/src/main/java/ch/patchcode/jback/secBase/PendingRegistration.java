package ch.patchcode.jback.secBase;

import org.inferred.freebuilder.FreeBuilder;

import java.time.Instant;

@FreeBuilder
public interface PendingRegistration {

    String getFirstName();

    String getLastName();

    VerificationMean getVerificationMean();

    String getVerificationCode();

    Instant getExpiresAt();

    class Builder extends PendingRegistration_Builder {
    }
}
