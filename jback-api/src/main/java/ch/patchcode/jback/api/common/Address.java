package ch.patchcode.jback.api.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.inferred.freebuilder.FreeBuilder;

import java.util.List;

@FreeBuilder
public interface Address {

    List<String> getLines();

    @JsonCreator
    static Address of(@JsonProperty("lines") List<String> lines) {
        return new Builder().addAllLines(lines).build();
    }

    static Address fromDomain(ch.patchcode.jback.core.common.Address address) {
        return new Builder()
                .addAllLines(address.getLines())
                .build();
    }

    default ch.patchcode.jback.core.common.Address toDomain() {
        return new ch.patchcode.jback.core.common.Address.Builder()
                .addAllLines(getLines())
                .build();
    }

    class Builder extends Address_Builder {
    }
}
