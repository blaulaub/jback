package ch.patchcode.jback.api.registration;

import ch.patchcode.jback.api.verification.VerificationMean;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;

/**
 * This is the record expected from people that want to register.
 * They have to provide their name, and some contact media.
 */
@ApiModel("personal information for registration")
@FreeBuilder
public abstract class InitialRegistrationData {

    @ApiModelProperty
    public abstract String getFirstName();

    @ApiModelProperty
    public abstract String getLastName();

    @ApiModelProperty
    public abstract VerificationMean getVerificationMean();

    @JsonCreator
    public static InitialRegistrationData create(
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

    public ch.patchcode.jback.security.secBaseImpl.InitialRegistrationData toDomain() {
        return new ch.patchcode.jback.security.secBaseImpl.InitialRegistrationData.Builder()
                .setFirstName(getFirstName())
                .setLastName(getLastName())
                .setVerificationMean(getVerificationMean().toDomain())
                .build();
    }

    public static class Builder extends InitialRegistrationData_Builder {
    }
}
