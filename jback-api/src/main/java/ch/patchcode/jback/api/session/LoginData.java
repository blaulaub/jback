package ch.patchcode.jback.api.session;

import ch.patchcode.jback.api.verification.VerificationMean;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;

@ApiModel("personal information for registration")
@FreeBuilder
public abstract class LoginData {

    @ApiModelProperty
    public abstract String getUserIdentification();

    @ApiModelProperty
    public abstract VerificationMean.Draft getVerificationMean();

    @JsonCreator
    public static LoginData create(
            @JsonProperty(value = "userIdentification", required = true) String userIdentification,
            @JsonProperty(value = "verificationMean", required = true) VerificationMean.Draft verificationMean
    ) {

        return new LoginData.Builder()
                .setUserIdentification(userIdentification)
                .setVerificationMean(verificationMean)
                .build();
    }

    public ch.patchcode.jback.presentation.LoginData toDomain() {
        return new ch.patchcode.jback.presentation.LoginData.Builder()
                .setUserIdentification(getUserIdentification())
                .setVerificationMean(getVerificationMean().toDomain())
                .build();
    }

    public static class Builder extends LoginData_Builder {
    }
}
