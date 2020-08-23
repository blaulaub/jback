package ch.patchcode.jback.core.registration;

import org.inferred.freebuilder.FreeBuilder;

/**
 * This is the record expected from people that want to register.
 * They have to provide their name, and some contact media.
 */
@FreeBuilder
public abstract class InitialRegistrationData {

    public abstract String getFirstName();

    public abstract String getLastName();

    public abstract VerificationMean getContactMean();

    public void accept(VerificationMean.VerificationMeanVisitor registrationHandler) {
        getContactMean().accept(registrationHandler);
    }

    public static class Builder extends InitialRegistrationData_Builder {
    }
}
