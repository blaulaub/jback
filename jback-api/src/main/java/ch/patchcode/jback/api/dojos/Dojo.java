package ch.patchcode.jback.api.dojos;

import ch.patchcode.jback.api.common.Address;
import org.inferred.freebuilder.FreeBuilder;

import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Dojo {

    UUID getId();
    String getName();
    Optional<Address> getAddress();

    class Builder extends Dojo_Builder {

    }
}
