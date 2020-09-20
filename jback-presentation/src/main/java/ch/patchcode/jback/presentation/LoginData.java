package ch.patchcode.jback.presentation;

import ch.patchcode.jback.security.entities.VerificationMean;
import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface LoginData {

    String getUserIdentification();

    VerificationMean getVerificationMean();

    default ch.patchcode.jback.security.LoginData toDomain() {

        return new ch.patchcode.jback.security.LoginData.Builder()
                .setUserIdentification(getUserIdentification())
                .setVerificationMean(getVerificationMean())
                .build();
    }

    class Builder extends LoginData_Builder {
    }
}
