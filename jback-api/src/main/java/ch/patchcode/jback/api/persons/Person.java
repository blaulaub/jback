package ch.patchcode.jback.api.persons;

import org.inferred.freebuilder.FreeBuilder;

import java.util.UUID;

@FreeBuilder
public interface Person {

    UUID id();
    String firstName();
    String lastName();

    class Builder extends Person_Builder {}
}
