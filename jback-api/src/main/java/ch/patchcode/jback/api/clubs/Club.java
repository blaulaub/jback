package ch.patchcode.jback.api.clubs;

import ch.patchcode.jback.api.persons.Person;
import ch.patchcode.jback.security.secBaseImpl.VerificationMean;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Club {

    static Club from(ch.patchcode.jback.core.clubs.Club<VerificationMean> club) {

        Builder builder = new Builder();

        builder.setId(club.getId())
                .setName(club.getName())
                .setUrl(club.getUrl());

        club.getContact().map(Person::fromDomain).ifPresent(builder::setContact);

        return builder.build();
    }

    @JsonCreator
    static Club create(
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

    UUID getId();

    String getName();

    @ApiModelProperty(dataType = "ch.patchcode.jback.api.persons.Person")
    Optional<Person> getContact();

    @ApiModelProperty(dataType = "java.net.URI")
    Optional<URI> getUrl();

    class Builder extends Club_Builder {

    }
}
