package ch.patchcode.jback.security.secBaseImpl;

import ch.patchcode.jback.security.entities.VerificationMean;
import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface InitialRegistrationData extends ch.patchcode.jback.secBase.InitialRegistrationData {

    @Override
    String getFirstName();

    @Override
    String getLastName();

    @Override
    VerificationMean getVerificationMean();

    class Builder extends InitialRegistrationData_Builder {
    }
}
