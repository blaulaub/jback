package ch.patchcode.jback.jpa.personalAuthentications;

import ch.patchcode.jback.jpa.persons.PersonJpa;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = PersonalAuthenticationJpa.ENTITY_NAME)
@Table(name = PersonalAuthenticationJpa.ENTITY_NAME)
public class PersonalAuthenticationJpa {

    public static final String ENTITY_NAME = "PersonalAuthentications";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    public UUID getId() {
        return id;
    }

    @OneToOne
    private PersonJpa self;

    @ManyToMany
    private List<PersonJpa> clients;

    @ElementCollection
    private List<String> authorities;

    public PersonJpa getSelf() {
        return self;
    }

    public void setSelf(PersonJpa self) {
        this.self = self;
    }

    public List<PersonJpa> getClients() {
        return clients;
    }

    public void setClients(List<PersonJpa> clients) {
        this.clients = clients;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
