package ch.patchcode.jback.api.clubs;

import ch.patchcode.jback.api.common.Address;
import org.inferred.freebuilder.FreeBuilder;

import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Club {

    UUID getId();
    String getName();
    Optional<Address> getAddress();

    class Builder extends Club_Builder {

    }
}
