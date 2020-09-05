package ch.patchcode.jback.api.session;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface SessionInfo {

    boolean isAuthenticated();

    String getPrincipalName();

    @JsonCreator
    static SessionInfo of(
            @JsonProperty("authenticated") boolean authenticated,
            @JsonProperty("principalName") String principalName
    ) {

        return new Builder()
                .setAuthenticated(authenticated)
                .setPrincipalName(principalName)
                .build();
    }

    class Builder extends SessionInfo_Builder {

    }
}
