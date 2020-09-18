package ch.patchcode.jback.secBase;

import ch.patchcode.jback.util.WithFirstAndLastName;
import org.inferred.freebuilder.FreeBuilder;

import java.time.Instant;

@FreeBuilder
public interface PendingRegistration extends WithFirstAndLastName {

    @Override
    String getFirstName();

    @Override
    String getLastName();

    VerificationMean getVerificationMean();

    String getVerificationCode();

    Instant getExpiresAt();

    class Builder extends PendingRegistration_Builder {
    }
}
