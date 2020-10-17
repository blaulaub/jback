package ch.patchcode.jback.api.roles;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public abstract class Role {

    public static Role fromDomain(ch.patchcode.jback.coreEntities.roles.Role role) {
        return new Builder()
                .build();
    }

    public static class Builder extends Role_Builder {}
}
