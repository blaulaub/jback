package ch.patchcode.jback.core.common;

import org.inferred.freebuilder.FreeBuilder;

import java.util.List;

@FreeBuilder
public interface Address {

    List<String> getLines();

    class Builder extends Address_Builder {}
}
