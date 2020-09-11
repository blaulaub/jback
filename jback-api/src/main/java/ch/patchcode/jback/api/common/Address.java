package ch.patchcode.jback.api.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface Address {

    String[] getLines();

    @JsonCreator
    static Address of(@JsonProperty("lines") String[] lines) {
        return new Builder().setLines(lines).build();
    }

    static Address fromDomain(ch.patchcode.jback.core.common.Address address) {
        return new Builder()
                .setLines(address.getLines().clone())
                .build();
    }

    default ch.patchcode.jback.core.common.Address toDomain() {
        return new ch.patchcode.jback.core.common.Address.Builder()
                .setLines(getLines().clone())
                .build();
    }

    class Builder extends Address_Builder {
    }
}
