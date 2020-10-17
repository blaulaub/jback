package ch.patchcode.jback.coreEntities.roles;

import ch.patchcode.jback.coreEntities.Authority;
import ch.patchcode.jback.coreEntities.Club;
import ch.patchcode.jback.secModel.Person;
import org.inferred.freebuilder.FreeBuilder;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;

/**
 * Role indicating that a person is a generic member of a club.
 */
@FreeBuilder
public abstract class MemberRole implements Role {

    @Override
    public abstract UUID getId();

    @Override
    public abstract Person getPerson();

    @Override
    public abstract Club getOrganisation();

    @Override
    public List<Authority> getPrivileges() {
        // we may add more rights later
        return emptyList();
    }

    public static class Builder extends MemberRole_Builder {
    }

    @FreeBuilder
    public abstract static class Draft implements Role.Draft {

        @Override
        public abstract Person getPerson();

        @Override
        public abstract Club getOrganisation();

        @Override
        public <R> R accept(Visitor<R> visitor) {

            return visitor.visit(this);
        }

        public static class Builder extends MemberRole_Draft_Builder {
        }
    }
}
