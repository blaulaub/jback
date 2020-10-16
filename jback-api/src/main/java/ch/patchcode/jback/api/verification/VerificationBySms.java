package ch.patchcode.jback.api.verification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;

import java.util.UUID;

/**
 * Registration by SMS, i.e., the user will be expected to
 * respond with a verification code sent by SMS.
 */
@ApiModel
@FreeBuilder
public abstract class VerificationBySms extends VerificationMean {

    public static final String TYPE = "sms";

    @ApiModelProperty
    @Override
    public abstract UUID getId();

    @ApiModelProperty
    public abstract String getPhoneNumber();

    @JsonCreator
    static VerificationBySms create(
            @JsonProperty("id") UUID id,
            @JsonProperty("phoneNumber") String phoneNumber
    ) {
        return new Builder()
                .setId(id)
                .setPhoneNumber(phoneNumber)
                .build();
    }

    public static class Builder extends VerificationBySms_Builder {
        @Override
        public VerificationBySms build() {
            setType(TYPE);
            return super.build();
        }
    }

    public ch.patchcode.jback.securityEntities.verificationMeans.VerificationBySms toDomain() {
        return new ch.patchcode.jback.securityEntities.verificationMeans.VerificationBySms.Builder()
                .setId(getId())
                .setPhoneNumber(getPhoneNumber())
                .build();
    }

    @ApiModel
    @FreeBuilder
    public abstract static class Draft extends VerificationMean.Draft {

        @ApiModelProperty
        public abstract String getPhoneNumber();

        @Override
        public ch.patchcode.jback.securityEntities.verificationMeans.VerificationBySms.Draft toDomain() {

            return new ch.patchcode.jback.securityEntities.verificationMeans.VerificationBySms.Draft.Builder()
                    .setPhoneNumber(getPhoneNumber())
                    .build();
        }

        @JsonCreator
        public static Draft create(
                @JsonProperty("phoneNumber") String phoneNumber
        ) {
            return new Draft.Builder()
                    .setPhoneNumber(phoneNumber)
                    .build();
        }

        public static class Builder extends VerificationBySms_Draft_Builder {
            @Override
            public VerificationBySms.Draft build() {
                setType(TYPE);
                return super.build();
            }
        }
    }
}
