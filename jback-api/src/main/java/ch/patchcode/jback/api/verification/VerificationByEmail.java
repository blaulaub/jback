package ch.patchcode.jback.api.verification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;

/**
 * Registration by email, i.e., the user will be expected to
 * respond with a verification code sent by email.
 */
@ApiModel
@FreeBuilder
public abstract class VerificationByEmail extends VerificationMean {

    public static final String TYPE = "email";

    @ApiModelProperty
    public abstract String getEmailAddress();

    @JsonCreator
    public static VerificationByEmail create(@JsonProperty("emailAddress") String emailAddress) {
        return new Builder().setEmailAddress(emailAddress).build();
    }

    public static class Builder extends VerificationByEmail_Builder {
        @Override
        public VerificationByEmail build() {
            setType(TYPE);
            return super.build();
        }
    }

    public ch.patchcode.jback.securityEntities.VerificationByEmail toDomain() {
        return new ch.patchcode.jback.securityEntities.VerificationByEmail.Builder()
                .setEmailAddress(getEmailAddress())
                .build();
    }

    @ApiModel
    @FreeBuilder
    public abstract static class Draft extends VerificationMean.Draft {

        @ApiModelProperty
        public abstract String getEmailAddress();

        @Override
        public ch.patchcode.jback.securityEntities.VerificationByEmail.Draft toDomain() {

            return new ch.patchcode.jback.securityEntities.VerificationByEmail.Draft.Builder()
                    .setEmailAddress(getEmailAddress())
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
