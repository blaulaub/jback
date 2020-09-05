package ch.patchcode.jback.api.registration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonCreator
    static InitialRegistrationData create(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("verificationMean") VerificationMean verificationMean
    ) {

        return new Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setVerificationMean(verificationMean)
                .build();
    }

    default ch.patchcode.jback.secBase.InitialRegistrationData toDomain() {
        return new ch.patchcode.jback.secBase.InitialRegistrationData.Builder()
                .setFirstName(getFirstName())
                .setLastName(getLastName())
                .setVerificationMean(getVerificationMean().toDomain())
                .build();
    }

    class Builder extends InitialRegistrationData_Builder {
    }
}
