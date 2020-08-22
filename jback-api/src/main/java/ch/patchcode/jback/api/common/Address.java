package ch.patchcode.jback.api.common;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface Address {

    static Address from(ch.patchcode.jback.core.common.Address address) {
        return new Builder()
                .setLines(address.getLines().clone())
                .build();
    }

    String[] getLines();

    class Builder extends Address_Builder {}
}
