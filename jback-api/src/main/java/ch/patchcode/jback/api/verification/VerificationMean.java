package ch.patchcode.jback.api.verification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = VerificationMean.VerificationByConsole.class, name = VerificationMean.VerificationByConsole.TYPE),
        @Type(value = VerificationMean.VerificationByEmail.class, name = VerificationMean.VerificationByEmail.TYPE),
        @Type(value = VerificationMean.VerificationBySms.class, name = VerificationMean.VerificationBySms.TYPE)
})
@ApiModel(subTypes = {
        VerificationMean.VerificationByConsole.class,
        VerificationMean.VerificationByEmail.class,
        VerificationMean.VerificationBySms.class
})
public abstract class VerificationMean {

    @ApiModelProperty
    public abstract String getType();

    public abstract ch.patchcode.jback.securityEntities.VerificationMean toDomain();

    /**
     * Registration by console, i.e., the user will be expected to
     * respond with a verification printed on the console. Obviously
     * only for development or debugging, not for anything practical.
     */
    @ApiModel
    @FreeBuilder
    public static abstract class VerificationByConsole extends VerificationMean {

        public static final String TYPE = "console";

        @JsonCreator
        public static VerificationByConsole create() {
            return new Builder().build();
        }

        public static class Builder extends VerificationMean_VerificationByConsole_Builder {
            @Override
            public VerificationByConsole build() {
                setType(TYPE);
                return super.build();
            }
        }

        public ch.patchcode.jback.securityEntities.VerificationByConsole toDomain() {
            return new ch.patchcode.jback.securityEntities.VerificationByConsole();
        }
    }

    /**
     * Registration by email, i.e., the user will be expected to
     * respond with a verification code sent by email.
     */
    @ApiModel
    @FreeBuilder
    public static abstract class VerificationByEmail extends VerificationMean {

        public static final String TYPE = "email";

        @ApiModelProperty
        public abstract String getEmailAddress();

        @JsonCreator
        public static VerificationByEmail create(@JsonProperty("emailAddress") String emailAddress) {
            return new Builder().setEmailAddress(emailAddress).build();
        }

        public static class Builder extends VerificationMean_VerificationByEmail_Builder {
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
    }

    /**
     * Registration by SMS, i.e., the user will be expected to
     * respond with a verification code sent by SMS.
     */
    @ApiModel
    @FreeBuilder
    public static abstract class VerificationBySms extends VerificationMean {

        public static final String TYPE = "sms";

        @ApiModelProperty
        public abstract String getPhoneNumber();

        @JsonCreator
        static VerificationBySms create(@JsonProperty("phoneNumber") String phoneNumber) {
            return new VerificationBySms.Builder().setPhoneNumber(phoneNumber).build();
        }

        public static class Builder extends VerificationMean_VerificationBySms_Builder {
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
    }
}
