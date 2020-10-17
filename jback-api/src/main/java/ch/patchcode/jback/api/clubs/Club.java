package ch.patchcode.jback.api.clubs;

import ch.patchcode.jback.api.persons.Person;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public abstract class Club {

    public static Club fromDomain(ch.patchcode.jback.coreEntities.Club club) {

        Builder builder = new Builder();

        builder.setId(club.getId())
                .setName(club.getName())
                .setUrl(club.getUrl());

        club.getContact().map(Person::fromDomain).ifPresent(builder::setContact);

        return builder.build();
    }

    @JsonCreator
    public static Club of(
            @JsonProperty(value = "id", required = true) UUID id,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty("url") URI url,
            @JsonProperty("contact") Person contact
    ) {

        return new Builder()
                .setId(id)
                .setName(name)
                .setUrl(Optional.ofNullable(url))
                .setContact(Optional.ofNullable(contact)).build();
    }

    public abstract UUID getId();

    public abstract String getName();

    @ApiModelProperty(dataType = "ch.patchcode.jback.api.persons.Person")
    public abstract Optional<Person> getContact();

    @ApiModelProperty(dataType = "java.net.URI")
    public abstract Optional<URI> getUrl();

    public static class Builder extends Club_Builder {

    }

    @ApiModel
    @FreeBuilder
    public static abstract class Draft {

        public abstract String getName();

        @ApiModelProperty(dataType = "ch.patchcode.jback.api.persons.Person")
        public abstract Optional<Person> getContact();

        @ApiModelProperty(dataType = "java.net.URI")
        public abstract Optional<URI> getUrl();

        @JsonCreator
        public static Draft of(
                @JsonProperty(value = "name", required = true) String name,
                @JsonProperty("url") URI url,
                @JsonProperty("contact") Person contact
        ) {

            return new Builder()
                    .setName(name)
                    .setUrl(Optional.ofNullable(url))
                    .setContact(Optional.ofNullable(contact)).build();
        }

        public ch.patchcode.jback.coreEntities.Club.Draft toDomain() {

            return new ch.patchcode.jback.coreEntities.Club.Draft.Builder()
                    .setName(getName())
                    .setUrl(getUrl())
                    .setContact(getContact().map(Person::toDomain))
                    .build();
        }

        public static class Builder extends Club_Draft_Builder {
        }
    }
}
