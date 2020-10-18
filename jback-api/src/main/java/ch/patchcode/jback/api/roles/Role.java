package ch.patchcode.jback.api.roles;

import ch.patchcode.jback.api.clubs.Club;
import ch.patchcode.jback.api.persons.Person;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MemberRole.class, name = MemberRole.TYPE)
})
@ApiModel(subTypes = {
        MemberRole.class
})
public abstract class Role {

    public abstract String getType();

    public abstract Person getPerson();

    public abstract Club getClub();

    public static Role fromDomain(ch.patchcode.jback.coreEntities.roles.Role role) {

        return role.accept(new ch.patchcode.jback.coreEntities.roles.Role.ResultVisitor<>() {

            @Override
            public Role visit(ch.patchcode.jback.coreEntities.roles.MemberRole memberRole) {
                return MemberRole.fromDomain(memberRole);
            }

            @Override
            public Role visit(ch.patchcode.jback.coreEntities.roles.AdminRole adminRole) {
                return AdminRole.fromDomain(adminRole);
            }
        });
    }
}
