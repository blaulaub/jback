package ch.patchcode.jback.api.session;

import ch.patchcode.jback.presentation.Perspective;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.inferred.freebuilder.FreeBuilder;

import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public abstract class SessionInfo {

    public abstract boolean isAuthenticated();

    public abstract String getPrincipalName();

    public abstract Perspective getPerspective();

    public abstract Optional<String> getFirstName();

    public abstract Optional<String> getLastName();

    /**
     * ID of the person as which the user authenticated.
     */
    public abstract Optional<UUID> getUserId();

    /**
     * ID of the person which the user currently impersonates.
     */
    public Optional<UUID> getPersonId() {
        return getUserId();
    };

    @JsonCreator
    public static SessionInfo of(
            @JsonProperty(value = "authenticated", required = true) boolean authenticated,
            @JsonProperty(value = "principalName", required = true) String principalName,
            @JsonProperty(value = "perspective", required = true) Perspective perspective,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("userId") UUID userId
    ) {

        Builder builder = new Builder();
        builder.setAuthenticated(authenticated);
        builder.setPrincipalName(principalName);
        builder.setPerspective(perspective);
        Optional.ofNullable(firstName).ifPresent(builder::setFirstName);
        Optional.ofNullable(lastName).ifPresent(builder::setLastName);
        Optional.ofNullable(userId).ifPresent(builder::setUserId);
        return builder.build();
    }

    public static class Builder extends SessionInfo_Builder {
    }
}
