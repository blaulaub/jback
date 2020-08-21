package ch.patchcode.jback.api.clubs;

import ch.patchcode.jback.api.persons.Person;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Club {

    UUID getId();

    String getName();

    @ApiModelProperty(dataType = "ch.patchcode.jback.api.persons.Person")
    Optional<Person> getContact();

    @ApiModelProperty(dataType = "java.net.URI")
    Optional<URI> getUrl();

    class Builder extends Club_Builder {

    }
}
