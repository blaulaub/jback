package ch.patchcode.jback.jpa.persons;

import ch.patchcode.jback.core.common.Address;
import ch.patchcode.jback.core.persons.Person;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

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

    private String address1;

    private String address2;

    private String address3;

    private String address4;

    private String address5;

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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getAddress5() {
        return address5;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
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
        // TODO my redundancy is higher than yours
        person.getAddress().map(Address::getLines).filter(it -> it.size() > 0).map(it -> it.get(0)).ifPresent(entity::setAddress1);
        person.getAddress().map(Address::getLines).filter(it -> it.size() > 1).map(it -> it.get(1)).ifPresent(entity::setAddress2);
        person.getAddress().map(Address::getLines).filter(it -> it.size() > 2).map(it -> it.get(2)).ifPresent(entity::setAddress3);
        person.getAddress().map(Address::getLines).filter(it -> it.size() > 3).map(it -> it.get(3)).ifPresent(entity::setAddress4);
        person.getAddress().map(Address::getLines).filter(it -> it.size() > 4).map(it -> it.get(4)).ifPresent(entity::setAddress5);
        return entity;
    }

    public Person toDomain() {

        Address.Builder addressBuilder = new Address.Builder();
        if (getAddress1() != null) {
            addressBuilder.addLines(getAddress1());
            {
                if (getAddress2() != null) {
                    addressBuilder.addLines(getAddress2());
                    {
                        if (getAddress3() != null) {
                            addressBuilder.addLines(getAddress3());
                            {
                                if (getAddress4() != null) {
                                    addressBuilder.addLines(getAddress4());
                                    {
                                        if (getAddress5() != null) {
                                            addressBuilder.addLines(getAddress5());
                                            // TODO my cyclomatic complexity is bigger than yours
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Address address = addressBuilder.build();
        return new ch.patchcode.jback.core.persons.Person.Builder()
                .setId(getId())
                .setFirstName(getFirstName())
                .setLastName(getLastName())
                .setAddress(address).build();
    }
}
