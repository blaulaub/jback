package ch.patchcode.jback.securityEntities;

import org.inferred.freebuilder.FreeBuilder;

import java.time.Instant;
import java.util.UUID;

@FreeBuilder
public interface PendingRegistration extends ch.patchcode.jback.secBase.PendingRegistration {

    UUID getId();

    @Override
    String getFirstName();

    @Override
    String getLastName();

    @Override
    VerificationMean.Draft getVerificationMean();

    @Override
    String getVerificationCode();

    @Override
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
