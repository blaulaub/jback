package ch.patchcode.jback.api.registration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.inferred.freebuilder.FreeBuilder;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = VerificationMean.VerificationByConsole.class, name = ch.patchcode.jback.api.registration.VerificationMean.VerificationByConsole.TYPE),
        @Type(value = VerificationMean.VerificationByEmail.class, name = ch.patchcode.jback.api.registration.VerificationMean.VerificationByEmail.TYPE),
        @Type(value = VerificationMean.VerificationBySms.class, name = ch.patchcode.jback.api.registration.VerificationMean.VerificationBySms.TYPE)
})
public interface VerificationMean {

    String getType();

    ch.patchcode.jback.sec.registration.VerificationMean toDomain();

    /**
     * Registration by console, i.e., the user will be expected to
     * respond with a verification printed on the console. Obviously
     * only for development or debugging, not for anything practical.
     */
    @FreeBuilder
    interface VerificationByConsole extends VerificationMean {

        String TYPE = "console";

        @JsonCreator
        static VerificationByConsole create() {
            return new Builder().build();
        }

        class Builder extends VerificationMean_VerificationByConsole_Builder {
            @Override
            public VerificationByConsole build() {
                setType(TYPE);
                return super.build();
            }
        }

        default ch.patchcode.jback.sec.registration.VerificationMean.VerificationByConsole toDomain() {
            return new ch.patchcode.jback.sec.registration.VerificationMean.VerificationByConsole();
        }
    }

    /**
     * Registration by email, i.e., the user will be expected to
     * respond with a verification code sent by email.
     */
    @FreeBuilder
    interface VerificationByEmail extends VerificationMean {

        String TYPE = "email";

        String getEmailAddress();

        @JsonCreator
        static VerificationByEmail create(@JsonProperty("emailAddress") String emailAddress) {
            return new Builder().setEmailAddress(emailAddress).build();
        }

        class Builder extends VerificationMean_VerificationByEmail_Builder {
            @Override
            public VerificationByEmail build() {
                setType(TYPE);
                return super.build();
            }
        }

        default ch.patchcode.jback.sec.registration.VerificationMean.VerificationByEmail toDomain() {
            return new ch.patchcode.jback.sec.registration.VerificationMean.VerificationByEmail.Builder()
                    .setEmailAddress(getEmailAddress())
                    .build();
        }
    }

    /**
     * Registration by SMS, i.e., the user will be expected to
     * respond with a verification code sent by SMS.
     */
    @FreeBuilder
    interface VerificationBySms extends VerificationMean {

        String TYPE = "sms";

        String getPhoneNumber();

        @JsonCreator
        static VerificationBySms create(@JsonProperty("phoneNumber") String phoneNumber) {
            return new VerificationBySms.Builder().setPhoneNumber(phoneNumber).build();
        }

        class Builder extends VerificationMean_VerificationBySms_Builder {
            @Override
            public VerificationBySms build() {
                setType(TYPE);
                return super.build();
            }
        }

        default ch.patchcode.jback.sec.registration.VerificationMean.VerificationBySms toDomain() {
            return new ch.patchcode.jback.sec.registration.VerificationMean.VerificationBySms.Builder()
                    .setPhoneNumber(getPhoneNumber())
                    .build();
        }
    }
}
