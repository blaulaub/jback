package ch.patchcode.jback.api.verification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;

@ApiModel
@FreeBuilder
public abstract class VerificationCode {

    @ApiModelProperty
    public abstract String getVerificationCode();

    @JsonCreator
    public static VerificationCode of(@JsonProperty("verificationCode") String verificationCode) {

        return new Builder().setVerificationCode(verificationCode).build();
    }

    public ch.patchcode.jback.security.verificationCodes.VerificationCode toDomain() {

        return  ch.patchcode.jback.security.verificationCodes.VerificationCode.of(getVerificationCode());
    }

    public static class Builder extends VerificationCode_Builder {}
}
