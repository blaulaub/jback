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
    public static ch.patchcode.jback.api.verification.VerificationByEmail create(@JsonProperty("emailAddress") String emailAddress) {
        return new Builder().setEmailAddress(emailAddress).build();
    }

    public static class Builder extends VerificationByEmail_Builder {
        @Override
        public ch.patchcode.jback.api.verification.VerificationByEmail build() {
            setType(TYPE);
            return super.build();
        }
    }

    public ch.patchcode.jback.securityEntities.VerificationByEmail toDomain() {
        return new ch.patchcode.jback.securityEntities.VerificationByEmail.Builder()
                .setEmailAddress(getEmailAddress())
                .build();
    }
}
