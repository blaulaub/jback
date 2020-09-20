package ch.patchcode.jback.api.session;

import ch.patchcode.jback.presentation.Perspective;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.inferred.freebuilder.FreeBuilder;

import java.util.Optional;

@FreeBuilder
public interface SessionInfo {

    boolean isAuthenticated();

    String getPrincipalName();

    Perspective getPerspective();

    Optional<String> getFirstName();

    Optional<String> getLastName();

    @JsonCreator
    static SessionInfo of(
            @JsonProperty(value = "authenticated", required = true) boolean authenticated,
            @JsonProperty(value = "principalName", required = true) String principalName,
            @JsonProperty(value = "principalName", required = true) Perspective perspective,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName
    ) {

        Builder builder = new Builder();
        builder.setAuthenticated(authenticated);
        builder.setPrincipalName(principalName);
        builder.setPerspective(perspective);
        Optional.ofNullable(firstName).ifPresent(builder::setFirstName);
        Optional.ofNullable(lastName).ifPresent(builder::setLastName);
        return builder.build();
    }

    class Builder extends SessionInfo_Builder {
    }
}
