package ch.patchcode.jback.api.roles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import org.inferred.freebuilder.FreeBuilder;

import java.util.List;

@FreeBuilder
@ApiModel
public abstract class Roles {

    public abstract List<Role> getRoles();

    @JsonCreator
    public static Roles of(
            @JsonProperty(value = "roles", required = true) List<Role> roles
    ) {

        return new Builder()
                .addAllRoles(roles)
                .build();
    }

    public static class Builder extends Roles_Builder {
    }

}
