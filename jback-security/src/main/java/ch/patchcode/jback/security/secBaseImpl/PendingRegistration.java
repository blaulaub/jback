package ch.patchcode.jback.security.secBaseImpl;

import org.inferred.freebuilder.FreeBuilder;

import java.time.Instant;

@FreeBuilder
public interface PendingRegistration extends ch.patchcode.jback.secBase.PendingRegistration {

    @Override
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
    interface Draft extends ch.patchcode.jback.secBase.PendingRegistration.Draft {

        @Override
        VerificationMean getVerificationMean();

        class Builder extends PendingRegistration_Draft_Builder {}
    }

    class Builder extends PendingRegistration_Builder {
    }
}
