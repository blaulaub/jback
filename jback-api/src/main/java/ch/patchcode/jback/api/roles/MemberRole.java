package ch.patchcode.jback.api.roles;


import ch.patchcode.jback.api.clubs.Club;
import ch.patchcode.jback.api.persons.Person;
import io.swagger.annotations.ApiModel;
import org.inferred.freebuilder.FreeBuilder;

@ApiModel
@FreeBuilder
public abstract class MemberRole extends Role {

    public static final String TYPE = "member";

    public static MemberRole fromDomain(ch.patchcode.jback.coreEntities.roles.MemberRole memberRole) {

        return new Builder()
                .setPerson(Person.fromDomain(memberRole.getPerson()))
                .setClub(Club.fromDomain(memberRole.getOrganisation()))
                .build();
    }

    public static class Builder extends MemberRole_Builder {

        @Override
        public MemberRole build() {
            setType(TYPE);
            return super.build();
        }
    }
}
