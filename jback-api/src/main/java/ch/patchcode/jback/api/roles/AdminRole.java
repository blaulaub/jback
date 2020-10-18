package ch.patchcode.jback.api.roles;


import ch.patchcode.jback.api.clubs.Club;
import ch.patchcode.jback.api.persons.Person;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import org.inferred.freebuilder.FreeBuilder;

@ApiModel
@FreeBuilder
public abstract class AdminRole extends Role {

    public static final String TYPE = "admin";

    public static AdminRole fromDomain(ch.patchcode.jback.coreEntities.roles.AdminRole adminRole) {

        return new Builder()
                .setPerson(Person.fromDomain(adminRole.getPerson()))
                .setClub(Club.fromDomain(adminRole.getOrganisation()))
                .build();
    }

    @JsonCreator
    public static AdminRole of(
            @JsonProperty("person") Person person,
            @JsonProperty("club") Club club
    ) {

        return new Builder()
                .setPerson(person)
                .setClub(club)
                .build();
    }

    public static class Builder extends AdminRole_Builder {

        @Override
        public AdminRole build() {
            setType(TYPE);
            return super.build();
        }
    }
}
