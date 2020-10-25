package ch.patchcode.jback.jpa.entities;

import ch.patchcode.jback.coreEntities.Address;
import ch.patchcode.jback.coreEntities.Person;

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
    @OrderBy("line")
    private List<AddressLine> addressLines;

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

    public static PersonJpa fromDomain(Person.Draft draft) {

        var result = new PersonJpa();
        result.setFirstName(draft.getFirstName());
        result.setLastName(draft.getLastName());
        draft.getAddress()
                .map(Address::getLines)
                .map(lines -> IntStream.range(0, lines.size())
                        .mapToObj(idx -> AddressLine.of(idx, lines.get(idx)))
                        .collect(toList()))
                .ifPresent(result::setAddressLines);
        return result;
    }

    public Person toDomain() {

        return new Person(
                getId(),
                getFirstName(),
                getLastName(),
                Optional.ofNullable(getAddressLines())
                        .map(it -> it.stream().map(AddressLine::getValue).collect(toList()))
                        .filter(it -> !it.isEmpty())
                        .map(Address::new)
                        .orElse(null));
    }
}
