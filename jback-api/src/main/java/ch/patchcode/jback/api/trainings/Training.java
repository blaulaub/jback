package ch.patchcode.jback.api.trainings;

import ch.patchcode.jback.api.dojos.Dojo;
import ch.patchcode.jback.api.persons.Person;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Training {

    UUID getId();

    Dojo getDojo();

    String getDiscipline();

    ZonedDateTime getBegin();

    ZonedDateTime getEnd();

    @ApiModelProperty(dataType = "ch.patchcode.jback.api.persons.Person")
    Optional<Person> getInstructor();

    class Builder extends Training_Builder {

    }
}
