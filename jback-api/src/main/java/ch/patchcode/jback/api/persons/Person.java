package ch.patchcode.jback.api.persons;

import ch.patchcode.jback.api.common.Address;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;

import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Person {

    UUID getId();

    String getFirstName();

    String getLastName();

    @ApiModelProperty(dataType = "ch.patchcode.jback.api.common.Address")
    Optional<Address> getAddress();

    @JsonCreator
    static Person of(
            @JsonProperty("id") UUID id,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("address") Address address
    ) {
        Builder builder = new Builder();
        builder.setId(id);
        builder.setFirstName(firstName);
        builder.setLastName(lastName);
        Optional.ofNullable(address).ifPresent(builder::setAddress);
        return builder.build();
    }

    static Person fromDomain(ch.patchcode.jback.core.persons.Person person) {

        Builder builder = new Builder();

        builder.setId(person.getId())
                .setFirstName(person.getFirstName())
                .setLastName(person.getLastName());
        person.getAddress().map(Address::fromDomain).ifPresent(builder::setAddress);

        return builder.build();
    }

    class Builder extends Person_Builder {
    }

    @FreeBuilder
    interface Draft {

        String getFirstName();

        String getLastName();

        @ApiModelProperty(dataType = "ch.patchcode.jback.api.common.Address")
        Optional<Address> getAddress();

        @JsonCreator
        static Draft of(
                @JsonProperty("firstName") String firstName,
                @JsonProperty("lastName") String lastName,
                @JsonProperty("address") Address address
        ) {
            var builder = new Builder();
            builder.setFirstName(firstName);
            builder.setLastName(lastName);
            Optional.ofNullable(address).ifPresent(it -> builder.setAddress(address));
            return builder.build();
        }

        default ch.patchcode.jback.core.persons.Person.Draft toDomain() {

            var builder = new ch.patchcode.jback.core.persons.Person.Draft.Builder();
            builder.setFirstName(getFirstName());
            builder.setLastName(getLastName());
            getAddress().map(Address::toDomain).ifPresent(builder::setAddress);
            return builder.build();
        }

        class Builder extends Person_Draft_Builder {
        }
    }
}
