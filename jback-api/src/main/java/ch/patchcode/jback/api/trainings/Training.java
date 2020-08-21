package ch.patchcode.jback.api.trainings;

import ch.patchcode.jback.api.dojos.Dojo;
import org.inferred.freebuilder.FreeBuilder;

import java.time.ZonedDateTime;
import java.util.UUID;

@FreeBuilder
public interface Training {

    UUID getId();
    Dojo getDojo();
    String getDiscipline();
    ZonedDateTime getBegin();
    ZonedDateTime getEnd();

    class Builder extends Training_Builder {

    }
}
