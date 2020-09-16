package ch.patchcode.jback.secBase;

import ch.patchcode.jback.util.WithFirstAndLastName;
import org.inferred.freebuilder.FreeBuilder;

/**
 * This is the record expected from people that want to register.
 * They have to provide their name, and some contact media.
 */
@FreeBuilder
public interface InitialRegistrationData extends WithFirstAndLastName {

    @Override
    String getFirstName();

    @Override
    String getLastName();

    VerificationMean getVerificationMean();

    class Builder extends InitialRegistrationData_Builder {
    }
}
