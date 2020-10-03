package ch.patchcode.jback.api.verification;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import org.inferred.freebuilder.FreeBuilder;

/**
 * Registration by console, i.e., the user will be expected to
 * respond with a verification printed on the console. Obviously
 * only for development or debugging, not for anything practical.
 */
@ApiModel
@FreeBuilder
public abstract class VerificationByConsole extends VerificationMean {

    public static final String TYPE = "console";

    @JsonCreator
    public static VerificationByConsole create() {
        return new Builder().build();
    }

    public static class Builder extends VerificationByConsole_Builder {
        @Override
        public VerificationByConsole build() {
            setType(TYPE);
            return super.build();
        }
    }

    public ch.patchcode.jback.securityEntities.VerificationByConsole toDomain() {
        return new ch.patchcode.jback.securityEntities.VerificationByConsole();
    }

    @ApiModel
    @FreeBuilder
    public abstract static class Draft extends VerificationMean.Draft {
    }
}
