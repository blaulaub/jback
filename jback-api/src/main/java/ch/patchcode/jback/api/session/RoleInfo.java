package ch.patchcode.jback.api.session;

import ch.patchcode.jback.coreEntities.roles.Role;
import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public abstract class RoleInfo {

    public static RoleInfo fromDomain(Role role) {
        return new Builder()
                .build();
    }

    public static class Builder extends RoleInfo_Builder {}
}
