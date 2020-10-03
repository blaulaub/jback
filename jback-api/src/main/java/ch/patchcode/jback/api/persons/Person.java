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
public interface Person {

    UUID getId();

    String getFirstName();

    String getLastName();

    List<String> getAddress();

    @JsonCreator
    static Person of(
            @JsonProperty("id") UUID id,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("address") List<String> address
    ) {
        Builder builder = new Builder();
        builder.setId(id);
        builder.setFirstName(firstName);
        builder.setLastName(lastName);
        builder.addAllAddress(address);
        return builder.build();
    }

    static Person fromDomain(ch.patchcode.jback.coreEntities.Person person) {

        Builder builder = new Builder();

        builder.setId(person.getId())
                .setFirstName(person.getFirstName())
                .setLastName(person.getLastName());
        person.getAddress().map(Address::getLines).ifPresent(builder::addAllAddress);

        return builder.build();
    }

    class Builder extends Person_Builder {
    }

    @ApiModel
    @FreeBuilder
    abstract class Draft {

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
            var builder = new Builder();
            builder.setFirstName(firstName);
            builder.setLastName(lastName);
            builder.addAllAddress(address);
            return builder.build();
        }

        public ch.patchcode.jback.coreEntities.Person.Draft toDomain() {

            var builder = new ch.patchcode.jback.coreEntities.Person.Draft.Builder();
            builder.setFirstName(getFirstName());
            builder.setLastName(getLastName());
            builder.setAddress(new Address.Builder()
                    .addAllLines(getAddress())
                    .build());
            return builder.build();
        }

        public static class Builder extends Person_Draft_Builder {
        }
    }
}
