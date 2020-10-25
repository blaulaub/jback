package ch.patchcode.jback.api.verification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;

import java.util.UUID;

/**
 * Registration by console, i.e., the user will be expected to
 * respond with a verification printed on the console. Obviously
 * only for development or debugging, not for anything practical.
 */
@ApiModel
@FreeBuilder
public abstract class VerificationByConsole extends VerificationMean {

    public static final String TYPE = "console";

    @ApiModelProperty
    @Override
    public abstract UUID getId();

    @JsonCreator
    public static VerificationByConsole create(
            @JsonProperty("id") UUID id
    ) {
        return new Builder()
                .setId(id)
                .build();
    }

    public static class Builder extends VerificationByConsole_Builder {
        @Override
        public VerificationByConsole build() {
            setType(TYPE);
            return super.build();
        }
    }

    public ch.patchcode.jback.securityEntities.verificationMeans.VerificationByConsole toDomain() {
        return new ch.patchcode.jback.securityEntities.verificationMeans.VerificationByConsole(getId());
    }

    @ApiModel
    @FreeBuilder
    public abstract static class Draft extends VerificationMean.Draft {

        @Override
        public ch.patchcode.jback.securityEntities.verificationMeans.VerificationByConsole.Draft toDomain() {

            return new ch.patchcode.jback.securityEntities.verificationMeans.VerificationByConsole.Draft();
        }

        @JsonCreator
        public static Draft create() {
            return new Draft.Builder()
                    .build();
        }

        public static class Builder extends VerificationByConsole_Draft_Builder {
            @Override
            public Draft build() {
                setType(TYPE);
                return super.build();
            }
        }
    }
}
