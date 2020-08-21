package ch.patchcode.jback.api.persons;

import ch.patchcode.jback.api.common.Address;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;

import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Person {

    UUID getId();

    String getFirstName();

    String getLastName();

    @ApiModelProperty(dataType = "ch.patchcode.jback.api.common.Address")
    Optional<Address> getAddress();

    class Builder extends Person_Builder {}
}
