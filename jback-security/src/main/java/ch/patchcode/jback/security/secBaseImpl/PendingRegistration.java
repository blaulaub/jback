package ch.patchcode.jback.security.secBaseImpl;

import ch.patchcode.jback.security.entities.VerificationMean;
import ch.patchcode.jback.util.WithFirstAndLastName;
import org.inferred.freebuilder.FreeBuilder;

import java.time.Instant;
import java.util.UUID;

@FreeBuilder
public interface PendingRegistration extends ch.patchcode.jback.secBase.PendingRegistration {

    Id getId();

    @Override
    String getFirstName();

    @Override
    String getLastName();

    @Override
    VerificationMean getVerificationMean();

    @Override
    String getVerificationCode();

    @Override
    Instant getExpiresAt();

    @FreeBuilder
    interface Id {

        UUID getId();

        static Id of(UUID id) {

            return new Builder().setId(id).build();
        }

        class Builder extends PendingRegistration_Id_Builder {
        }
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

        class Builder extends PendingRegistration_Draft_Builder {
        }
    }

    class Builder extends PendingRegistration_Builder {
    }
}
