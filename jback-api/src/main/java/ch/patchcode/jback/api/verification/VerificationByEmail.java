package ch.patchcode.jback.api.verification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;

import java.util.UUID;

/**
 * Registration by email, i.e., the user will be expected to
 * respond with a verification code sent by email.
 */
@ApiModel
@FreeBuilder
public abstract class VerificationByEmail extends VerificationMean {

    public static final String TYPE = "email";

    @ApiModelProperty
    @Override
    public abstract UUID getId();

    @ApiModelProperty
    public abstract String getEmailAddress();

    @JsonCreator
    public static VerificationByEmail create(
            @JsonProperty("id") UUID id,
            @JsonProperty("emailAddress") String emailAddress
    ) {
        return new Builder()
                .setId(id)
                .setEmailAddress(emailAddress)
                .build();
    }

    public static class Builder extends VerificationByEmail_Builder {
        @Override
        public VerificationByEmail build() {
            setType(TYPE);
            return super.build();
        }
    }

    public ch.patchcode.jback.securityEntities.verificationMeans.VerificationByEmail toDomain() {
        return new ch.patchcode.jback.securityEntities.verificationMeans.VerificationByEmail(
                getId(), getEmailAddress());
    }

    @ApiModel
    @FreeBuilder
    public abstract static class Draft extends VerificationMean.Draft {

        @ApiModelProperty
        public abstract String getEmailAddress();

        @Override
        public ch.patchcode.jback.securityEntities.verificationMeans.VerificationByEmail.Draft toDomain() {

            return new ch.patchcode.jback.securityEntities.verificationMeans.VerificationByEmail.Draft(getEmailAddress());
        }

        @JsonCreator
        public static Draft create(
                @JsonProperty("emailAddress") String emailAddress
        ) {
            return new Builder()
                    .setEmailAddress(emailAddress)
                    .build();
        }

        public static class Builder extends VerificationByEmail_Draft_Builder {
            @Override
            public Draft build() {
                setType(TYPE);
                return super.build();
            }
        }
    }
}
