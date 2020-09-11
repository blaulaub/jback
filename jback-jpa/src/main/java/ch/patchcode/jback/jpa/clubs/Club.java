package ch.patchcode.jback.jpa.clubs;

import ch.patchcode.jback.jpa.persons.PersonJpa;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    public UUID getId() {
        return id;
    }

    private String name;

    private String uri;

    @ManyToOne
    private PersonJpa contact;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public PersonJpa getContact() {
        return contact;
    }

    public void setContact(PersonJpa contact) {
        this.contact = contact;
    }
}
