package ch.patchcode.jback.securityEntities.registration;

import ch.patchcode.jback.securityEntities.verificationMeans.VerificationMean;
import org.inferred.freebuilder.FreeBuilder;

import java.time.Instant;
import java.util.UUID;

@FreeBuilder
public interface PendingRegistration {

    UUID getId();

    String getFirstName();

    String getLastName();

    VerificationMean.Draft getVerificationMean();

    String getVerificationCode();

    Instant getExpiresAt();

    @FreeBuilder
    interface Draft {

        String getFirstName();

        String getLastName();

        VerificationMean.Draft getVerificationMean();

        String getVerificationCode();

        Instant getExpiresAt();

        class Builder extends PendingRegistration_Draft_Builder {
        }
    }

    class Builder extends PendingRegistration_Builder {
    }
}
