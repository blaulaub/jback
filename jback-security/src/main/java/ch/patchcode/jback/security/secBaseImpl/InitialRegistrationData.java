package ch.patchcode.jback.security.secBaseImpl;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface InitialRegistrationData extends ch.patchcode.jback.secBase.InitialRegistrationData {

    VerificationMean getVerificationMean();

    class Builder extends InitialRegistrationData_Builder {
    }
}
