package ch.patchcode.jback.sec.registration;

import org.inferred.freebuilder.FreeBuilder;

import java.time.Instant;
import java.util.UUID;

@FreeBuilder
public interface PendingRegistration {

    String getFirstName();

    String getLastName();

    VerificationMean getVerificationMean();

    String getVerificationCode();

    Instant getExpiresAt();

    class Builder extends PendingRegistration_Builder {
    }

    @FreeBuilder
    interface Id {

        UUID getId();

        static Id of(UUID id) {

            return new Builder().setId(id).build();
        }

        class Builder extends PendingRegistration_Id_Builder {}
    }
}
