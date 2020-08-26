package ch.patchcode.jback.api.clubs;

import ch.patchcode.jback.api.persons.Person;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Club {

    static Club from(ch.patchcode.jback.core.clubs.Club club) {

        Builder builder = new Builder();

        builder.setId(club.getId())
                .setName(club.getName())
                .setUrl(club.getUrl());

        club.getContact().map(Person::from).ifPresent(builder::setContact);

        return builder.build();
    }

    @JsonCreator
    static Club create(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("url") Optional<URI> url,
            @JsonProperty("contact") Optional<Person> contact
    ) {

        Builder builder = new Builder();

        builder.setId(id)
                .setName(name)
                .setUrl(url);

        contact.ifPresent(builder::setContact);

        return builder.build();
    }

    UUID getId();

    String getName();

    @ApiModelProperty(dataType = "ch.patchcode.jback.api.persons.Person")
    Optional<Person> getContact();

    @ApiModelProperty(dataType = "java.net.URI")
    Optional<URI> getUrl();

    class Builder extends Club_Builder {

    }
}
