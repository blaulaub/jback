package ch.patchcode.jback.jpa.persons;

import ch.patchcode.jback.core.common.Address;
import ch.patchcode.jback.core.persons.Person;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Entity(name = PersonJpa.ENTITY_NAME)
@Table(name = PersonJpa.ENTITY_NAME)
public class PersonJpa {

    public final static String ENTITY_NAME = "Persons";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    public UUID getId() {
        return id;
    }

    private String firstName;

    private String lastName;

    @ElementCollection
    @OrderBy("address.order")
    private List<AddressLine> addressLines;

    @ElementCollection
    private List<String> authorities;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<AddressLine> getAddressLines() {
        return addressLines;
    }

    public void setAddressLines(List<AddressLine> addressLines) {
        this.addressLines = addressLines;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public static PersonJpa fromDomain(Person person) {

        var entity = new PersonJpa();
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());

        person.getAddress()
                .map(Address::getLines)
                .map(lines -> IntStream.range(0, lines.size())
                        .mapToObj(idx -> AddressLine.of(idx, lines.get(idx)))
                        .collect(toList()))
                .ifPresent(entity::setAddressLines);
        return entity;
    }

    public Person toDomain() {

        Person.Builder builder = new Person.Builder()
                .setId(getId())
                .setFirstName(getFirstName())
                .setLastName(getLastName());
        Optional.ofNullable(getAddressLines())
                .map(it -> it.stream().map(AddressLine::getValue))
                .map(it -> new Address.Builder().addAllLines(it).build())
                .ifPresent(builder::setAddress);
        return builder.build();
    }

    @Embeddable
    public static class AddressLine {

        @Column(unique = true)
        private int order;

        private String value;

        public static AddressLine of(int order, String value) {

            var result = new AddressLine();
            result.setOrder(order);
            result.setValue(value);
            return result;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
