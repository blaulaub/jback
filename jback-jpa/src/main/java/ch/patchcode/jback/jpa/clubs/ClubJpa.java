package ch.patchcode.jback.jpa.clubs;

import ch.patchcode.jback.core.clubs.Club;
import ch.patchcode.jback.jpa.persons.PersonJpa;

import javax.persistence.*;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Entity(name = ClubJpa.ENTITY_NAME)
@Table(name = ClubJpa.ENTITY_NAME)
public class ClubJpa {

    public final static String ENTITY_NAME = "Clubs";

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

    public Club toDomain() {
        Club.Builder builder = new Club.Builder()
                .setId(getId())
                .setName(getName());
        Optional.ofNullable(getContact()).map(PersonJpa::toDomain).ifPresent(builder::setContact);
        Optional.ofNullable(getUri()).map(URI::create).ifPresent(builder::setUrl);
        return builder.build();
    }
}
