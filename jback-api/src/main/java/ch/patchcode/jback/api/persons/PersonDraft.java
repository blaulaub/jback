package ch.patchcode.jback.api.persons;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface PersonDraft {

    String getFirstName();

    String getLastName();

    class Builder extends PersonDraft_Builder {
    }
}
