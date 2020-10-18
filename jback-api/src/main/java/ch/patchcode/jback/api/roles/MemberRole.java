package ch.patchcode.jback.api.roles;


import ch.patchcode.jback.api.clubs.Club;
import ch.patchcode.jback.api.persons.Person;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import org.inferred.freebuilder.FreeBuilder;

@ApiModel
@FreeBuilder
public abstract class MemberRole extends Role {

    public static final String TYPE = "member";

    public ch.patchcode.jback.coreEntities.roles.MemberRole toDomain() {

        return new ch.patchcode.jback.coreEntities.roles.MemberRole.Builder()
                .setId(getId())
                .setPerson(getPerson().toDomain())
                .setOrganisation(getClub().toDomain())
                .build();
    }

    public static MemberRole fromDomain(ch.patchcode.jback.coreEntities.roles.MemberRole memberRole) {

        return new Builder()
                .setId(memberRole.getId())
                .setPerson(Person.fromDomain(memberRole.getPerson()))
                .setClub(Club.fromDomain(memberRole.getOrganisation()))
                .build();
    }

    @JsonCreator
    public static MemberRole of(
            @JsonProperty("person") Person person,
            @JsonProperty("club") Club club
    ) {

        return new Builder()
                .setPerson(person)
                .setClub(club)
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
