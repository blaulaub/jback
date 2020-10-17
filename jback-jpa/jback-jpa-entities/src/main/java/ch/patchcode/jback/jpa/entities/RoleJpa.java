package ch.patchcode.jback.jpa.entities;

import ch.patchcode.jback.coreEntities.roles.MemberRole;
import ch.patchcode.jback.coreEntities.roles.Role;
import ch.patchcode.jback.securityEntities.verificationMeans.*;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = RoleJpa.ENTITY_NAME)
@Table(name = RoleJpa.ENTITY_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class RoleJpa {

    public static final String ENTITY_NAME = "Roles";

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

    public abstract <R> R accept(Visitor<R> visitor);

    public interface Visitor<R> {

        R visit(MemberRoleJpa memberRoleJpa);
    }

    @Entity
    @DiscriminatorValue("member")
    public static class MemberRoleJpa extends RoleJpa {

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }
}
