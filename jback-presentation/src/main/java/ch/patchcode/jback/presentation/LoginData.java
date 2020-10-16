package ch.patchcode.jback.presentation;

import ch.patchcode.jback.securityEntities.verificationMeans.VerificationMean;
import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface LoginData {

    String getUserIdentification();

    VerificationMean.Draft getVerificationMean();

    default ch.patchcode.jback.security.LoginData toDomain() {

        return new ch.patchcode.jback.security.LoginData.Builder()
                .setUserIdentification(getUserIdentification())
                .setVerificationMean(getVerificationMean())
                .build();
    }

    class Builder extends LoginData_Builder {
    }
}
