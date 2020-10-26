package ch.patchcode.jback.api.persons;

import ch.patchcode.jback.coreEntities.Address;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.inferred.freebuilder.FreeBuilder;

import java.util.List;
import java.util.UUID;

@FreeBuilder
public abstract class Person {

    public abstract UUID getId();

    public abstract String getFirstName();

    public abstract String getLastName();

    public abstract List<String> getAddress();

    @JsonCreator
    public static Person of(
            @JsonProperty(value = "id", required = true) UUID id,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("address") List<String> address
    ) {
        return new Builder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .addAllAddress(address)
                .build();
    }

    public static Person fromDomain(ch.patchcode.jback.coreEntities.Person person) {

        Builder builder = new Builder();

        builder.setId(person.getId())
                .setFirstName(person.getFirstName())
                .setLastName(person.getLastName());
        person.getAddress().map(Address::getLines).ifPresent(builder::addAllAddress);

        return builder.build();
    }

    public ch.patchcode.jback.coreEntities.Person toDomain() {
        return new ch.patchcode.jback.coreEntities.Person(
                getId(),
                getFirstName(),
                getLastName(),
                new Address(getAddress()));
    }

    public static class Builder extends Person_Builder {
    }

    @ApiModel
    @FreeBuilder
    public abstract static class Draft {

        @ApiModelProperty
        public abstract String getFirstName();

        @ApiModelProperty
        public abstract String getLastName();

        public abstract List<String> getAddress();

        @JsonCreator
        public static Draft of(
                @JsonProperty("firstName") String firstName,
                @JsonProperty("lastName") String lastName,
                @JsonProperty("address") List<String> address
        ) {
            return new Builder()
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .addAllAddress(address)
                    .build();
        }

        public ch.patchcode.jback.coreEntities.Person.Draft toDomain() {

            var builder = new ch.patchcode.jback.coreEntities.Person.Draft.Builder();
            builder.setFirstName(getFirstName());
            builder.setLastName(getLastName());
            builder.setAddress(new Address(getAddress()));
            return builder.build();
        }

        public static class Builder extends Person_Draft_Builder {
        }
    }

}
