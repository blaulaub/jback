package ch.patchcode.jback.api.clubs;

import ch.patchcode.jback.api.persons.Person;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.inferred.freebuilder.FreeBuilder;

import java.util.UUID;

@FreeBuilder
public abstract class ClubMembershipApplication {

    public abstract UUID getId();

    public abstract Person getPerson();

    public abstract Club getClub();

    @FreeBuilder
    public static abstract class Draft {

        public abstract Person getPerson();

        public abstract Club getClub();

        @JsonCreator
        public static Draft of(
                @JsonProperty(value = "person", required = true) Person person,
                @JsonProperty(value = "club", required = true) Club club
        ) {

            return new ClubMembershipApplication.Draft.Builder()
                    .setPerson(person)
                    .setClub(club)
                    .build();
        }

        public ch.patchcode.jback.coreEntities.ClubMembershipApplication.Draft toDomain() {

            return new ch.patchcode.jback.coreEntities.ClubMembershipApplication.Draft.Builder()
                    .setPerson(getPerson().toDomain())
                    .setClub(getClub().toDomain())
                    .build();
        }

        public static class Builder extends ClubMembershipApplication_Draft_Builder {
        }
    }

    public static class Builder extends ClubMembershipApplication_Builder {
    }
}
