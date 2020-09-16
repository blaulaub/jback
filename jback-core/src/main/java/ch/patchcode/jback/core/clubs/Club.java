package ch.patchcode.jback.core.clubs;

import ch.patchcode.jback.core.persons.Person;
import org.inferred.freebuilder.FreeBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Club {

    UUID getId();

    String getName();

    Optional<Person> getContact();

    Optional<URI> getUrl();

    class Builder extends Club_Builder {
    }

    @FreeBuilder
    interface Draft {

        String getName();

        Optional<Person> getContact();

        Optional<URI> getUrl();

        class Builder extends Club_Draft_Builder {
        }
    }
}
