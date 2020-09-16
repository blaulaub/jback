package ch.patchcode.jback.secBase;

import ch.patchcode.jback.util.WithFirstAndLastName;
import org.inferred.freebuilder.FreeBuilder;

import java.time.Instant;
import java.util.UUID;

@FreeBuilder
public interface PendingRegistration extends WithFirstAndLastName {

    Id getId();

    @Override
    String getFirstName();

    @Override
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

    @FreeBuilder
    interface Draft extends WithFirstAndLastName {

        @Override
        String getFirstName();

        @Override
        String getLastName();

        VerificationMean getVerificationMean();

        String getVerificationCode();

        Instant getExpiresAt();

        class Builder extends PendingRegistration_Draft_Builder {}
    }
}
