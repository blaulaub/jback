package ch.patchcode.jback.api.persons;

import ch.patchcode.jback.securityEntities.verificationMeans.VerificationByPassword;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.inferred.freebuilder.FreeBuilder;

import java.util.List;

@FreeBuilder
public abstract class PersonWithPasswordDraft extends Person.Draft {

    public abstract String getUsername();

    public abstract String getPassword();

    @JsonCreator
    public static PersonWithPasswordDraft of(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("address") List<String> address,
            @JsonProperty("username") String username,
            @JsonProperty("password") String password
    ) {
        return new Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .addAllAddress(address)
                .setUsername(username)
                .setPassword(password)
                .build();
    }

    public VerificationByPassword.Draft toVerificationMean() {
        return new VerificationByPassword.Draft.Builder()
                .setUsername(getUsername())
                .setPassword(getPassword())
                .build();
    }

    public static class Builder extends PersonWithPasswordDraft_Builder {
    }
}
