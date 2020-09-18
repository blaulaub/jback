package ch.patchcode.jback.security.secBaseImpl;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface PendingRegistration extends ch.patchcode.jback.secBase.PendingRegistration {

    @Override
    VerificationMean getVerificationMean();

    @FreeBuilder
    interface Draft extends ch.patchcode.jback.secBase.PendingRegistration.Draft {

        @Override
        VerificationMean getVerificationMean();

        class Builder extends PendingRegistration_Draft_Builder {}
    }

    class Builder extends PendingRegistration_Builder {
    }
}
