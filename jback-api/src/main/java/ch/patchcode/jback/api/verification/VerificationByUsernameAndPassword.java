package ch.patchcode.jback.api.verification;

import ch.patchcode.jback.securityEntities.verificationMeans.VerificationByPassword;
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
public abstract class VerificationByUsernameAndPassword extends VerificationMean {

    public static final String TYPE = "password";

    @ApiModelProperty
    @Override
    public abstract UUID getId();

    @ApiModelProperty
    public abstract String getUsername();

    @ApiModelProperty
    public abstract String getPassword();

    @JsonCreator
    public static VerificationByUsernameAndPassword create(
            @JsonProperty("id") UUID id,
            @JsonProperty("username") String username,
            @JsonProperty("password") String password
    ) {
        return new Builder()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .build();
    }

    public static class Builder extends VerificationByUsernameAndPassword_Builder {
        @Override
        public VerificationByUsernameAndPassword build() {
            setType(TYPE);
            return super.build();
        }
    }

    public VerificationByPassword toDomain() {
        return new VerificationByPassword(
                getId(),
                getUsername(),
                getPassword()
        );
    }

    @ApiModel
    @FreeBuilder
    public abstract static class Draft extends VerificationMean.Draft {

        @ApiModelProperty
        public abstract String getUsername();

        @ApiModelProperty
        public abstract String getPassword();

        @Override
        public VerificationByPassword.Draft toDomain() {

            return new VerificationByPassword.Draft(getUsername(), getPassword());
        }

        @JsonCreator
        public static Draft create(
                @JsonProperty("username") String username,
                @JsonProperty("password") String password
        ) {
            return new Builder()
                    .setUsername(username)
                    .setPassword(password)
                    .build();
        }

        public static class Builder extends VerificationByUsernameAndPassword_Draft_Builder {
            @Override
            public Draft build() {
                setType(TYPE);
                return super.build();
            }
        }
    }
}
