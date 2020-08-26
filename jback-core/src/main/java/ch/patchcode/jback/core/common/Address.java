package ch.patchcode.jback.core.common;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface Address {

    String[] getLines();

    class Builder extends Address_Builder {}
}
