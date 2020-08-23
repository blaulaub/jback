package ch.patchcode.jback.api.registration;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.inferred.freebuilder.FreeBuilder;

/**
 * This is the record expected from people that want to register.
 * They have to provide their name, and some contact media.
 */
@FreeBuilder
public interface InitialRegistrationData {

    String getFirstName();

    String getLastName();

    VerificationContact getContactMean();

    @JsonCreator
    static InitialRegistrationData create(
            String firstName,
            String lastName,
            VerificationContact contactMean
    ) {

        return new Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setContactMean(contactMean)
                .build();
    }

    default ch.patchcode.jback.core.registration.InitialRegistrationData toDomain() {
        return new ch.patchcode.jback.core.registration.InitialRegistrationData.Builder()
                .setFirstName(getFirstName())
                .setLastName(getLastName())
                .setContactMean(getContactMean().toDomain())
                .build();
    }

    class Builder extends InitialRegistrationData_Builder {
    }
}
