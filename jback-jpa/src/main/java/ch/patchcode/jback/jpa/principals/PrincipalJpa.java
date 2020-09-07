package ch.patchcode.jback.jpa.principals;

import ch.patchcode.jback.jpa.persons.PersonJpa;
import ch.patchcode.jback.jpa.verificationMeans.VerificationMeanJpa;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = PrincipalJpa.ENTITY_NAME)
@Table(name = PrincipalJpa.ENTITY_NAME)
public class PrincipalJpa {

    public static final String ENTITY_NAME = "Principals";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    public UUID getId() {
        return id;
    }

    @ManyToMany
    private List<PersonJpa> persons;

    @ElementCollection
    private List<String> authorities;

    public List<PersonJpa> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonJpa> persons) {
        this.persons = persons;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}