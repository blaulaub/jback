package ch.patchcode.jback.jpa.entities;

import ch.patchcode.jback.coreEntities.ClubMembershipApplication;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = ClubMembershipApplicationJpa.ENTITY_NAME)
@Table(name = ClubMembershipApplicationJpa.ENTITY_NAME)
public class ClubMembershipApplicationJpa {

    public final static String ENTITY_NAME = "MemberApplication";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    public UUID getId() {
        return id;
    }

    @ManyToOne
    private PersonJpa person;

    @ManyToOne
    private ClubJpa club;

    public PersonJpa getPerson() {
        return person;
    }

    public void setPerson(PersonJpa person) {
        this.person = person;
    }

    public ClubJpa getClub() {
        return club;
    }

    public void setClub(ClubJpa club) {
        this.club = club;
    }

    public ClubMembershipApplication toDomain() {

        return new ClubMembershipApplication.Builder()
                .setId(getId())
                .setPerson(getPerson().toDomain())
                .setClub(getClub().toDomain())
                .build();
    }

}
