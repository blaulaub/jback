package ch.patchcode.jback.api.persons;

import ch.patchcode.jback.api.common.Address;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;

import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Person extends PersonDraft {

    static Person from(ch.patchcode.jback.core.persons.Person person) {

        Builder builder = new Builder();

        builder.setId(person.getId())
                .setFirstName(person.getFirstName())
                .setLastName(person.getLastName());
        person.getAddress().map(Address::from).ifPresent(builder::setAddress);

        return builder.build();
    }

    UUID getId();

    @ApiModelProperty(dataType = "ch.patchcode.jback.api.common.Address")
    Optional<Address> getAddress();

    class Builder extends Person_Builder {
    }
}
