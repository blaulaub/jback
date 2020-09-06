package ch.patchcode.jback.jpa.persons;

import ch.patchcode.jback.core.common.Address;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class PersonJpa {

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

    public ch.patchcode.jback.core.persons.Person toDomain() {
        return new ch.patchcode.jback.core.persons.Person.Builder()
                .setId(getId())
                .setFirstName(getFirstName())
                .setLastName(getLastName())
                .setAddress(new Address.Builder().setLines(new String[]{
                        getAddress1(),
                        getAddress2(),
                        getAddress3(),
                        getAddress4(),
                        getAddress5()
                }).build()).build();
    }
}
