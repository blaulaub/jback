package ch.patchcode.jback.jpa.personalAuthentications;

import ch.patchcode.jback.jpa.persons.PersonJpa;
import ch.patchcode.jback.jpa.verificationMeans.VerificationMeanJpa;
import ch.patchcode.jback.security.authentications.PersonalAuthentication;

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

    @OneToMany(mappedBy = "personalAuthentication")
    private List<VerificationMeanJpa> verificationMeans;

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

    public List<VerificationMeanJpa> getVerificationMeans() {
        return verificationMeans;
    }

    public void setVerificationMeans(List<VerificationMeanJpa> verificationMeans) {

        this.verificationMeans = verificationMeans;
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

    public PersonalAuthentication toDomain() {

        return new PersonalAuthentication.Builder()
                .setHolder(getSelf().toDomain())
                .addAllMeans(getVerificationMeans().stream().map(VerificationMeanJpa::toDomain))
                .build();
    }
}
