package ch.patchcode.jback.security.registration;

import org.inferred.freebuilder.FreeBuilder;

/**
 * This is the record expected from people that want to register.
 * They have to provide their name, and some contact media.
 */
@FreeBuilder
public interface InitialRegistrationData {

    String getFirstName();

    String getLastName();

    VerificationMean getVerificationMean();

    class Builder extends InitialRegistrationData_Builder {
    }
}
