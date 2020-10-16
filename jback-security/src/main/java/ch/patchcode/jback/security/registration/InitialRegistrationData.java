package ch.patchcode.jback.security.registration;

import ch.patchcode.jback.securityEntities.verificationMeans.VerificationMean;
import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface InitialRegistrationData {

    String getFirstName();

    String getLastName();

    VerificationMean.Draft getVerificationMean();

    class Builder extends InitialRegistrationData_Builder {
    }
}
