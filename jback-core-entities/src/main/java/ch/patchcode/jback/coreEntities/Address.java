package ch.patchcode.jback.coreEntities;

import org.inferred.freebuilder.FreeBuilder;

import java.util.List;

@FreeBuilder
public interface Address {

    List<String> getLines();

    class Builder extends Address_Builder {}
}