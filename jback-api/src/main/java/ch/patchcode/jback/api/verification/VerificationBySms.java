package ch.patchcode.jback.api.verification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;

/**
 * Registration by SMS, i.e., the user will be expected to
 * respond with a verification code sent by SMS.
 */
@ApiModel
@FreeBuilder
public abstract class VerificationBySms extends VerificationMean {

    public static final String TYPE = "sms";

    @ApiModelProperty
    public abstract String getPhoneNumber();

    @JsonCreator
    static VerificationBySms create(@JsonProperty("phoneNumber") String phoneNumber) {
        return new Builder().setPhoneNumber(phoneNumber).build();
    }

    public static class Builder extends VerificationBySms_Builder {
        @Override
        public VerificationBySms build() {
            setType(TYPE);
            return super.build();
        }
    }

    public ch.patchcode.jback.securityEntities.VerificationBySms toDomain() {
        return new ch.patchcode.jback.securityEntities.VerificationBySms.Builder()
                .setPhoneNumber(getPhoneNumber())
                .build();
    }

    @ApiModel
    @FreeBuilder
    public abstract static class Draft extends VerificationMean.Draft {
    }
}
