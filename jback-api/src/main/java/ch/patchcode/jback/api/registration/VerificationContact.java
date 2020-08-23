package ch.patchcode.jback.api.registration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.inferred.freebuilder.FreeBuilder;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = VerificationContact.EmailContact.class, name = VerificationContact.EmailContact.TYPE),
        @Type(value = VerificationContact.SmsContact.class, name = VerificationContact.SmsContact.TYPE)
})
public interface VerificationContact {

    String getType();

    @FreeBuilder
    interface EmailContact extends VerificationContact {

        String TYPE = "email";

        String getEmailAddress();

        @JsonCreator
        static EmailContact create(@JsonProperty("emailAddress") String emailAddress) {
            return new Builder().setEmailAddress(emailAddress).build();
        }

        class Builder extends VerificationContact_EmailContact_Builder {
            @Override
            public EmailContact build() {
                setType(TYPE);
                return super.build();
            }
        }
    }

    @FreeBuilder
    interface SmsContact extends VerificationContact {

        String TYPE = "sms";

        String getPhoneNumber();

        @JsonCreator
        static SmsContact create(@JsonProperty("phoneNumber") String phoneNumber) {
            return new SmsContact.Builder().setPhoneNumber(phoneNumber).build();
        }

        class Builder extends VerificationContact_SmsContact_Builder {
            @Override
            public SmsContact build() {
                setType(TYPE);
                return super.build();
            }
        }
    }
}
