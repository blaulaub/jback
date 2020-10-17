package ch.patchcode.jback.coreEntities.roles;

import ch.patchcode.jback.coreEntities.Authority;
import ch.patchcode.jback.coreEntities.Club;
import ch.patchcode.jback.secModel.Person;

import java.util.List;
import java.util.UUID;

public interface Role extends ch.patchcode.jback.secModel.Role<Club, Person, Authority> {

    UUID getId();

    @Override
    Person getPerson();

    @Override
    Club getOrganisation();

    @Override
    List<Authority> getPrivileges();

    interface Draft {

        Person getPerson();

        Club getOrganisation();

        <R> R accept(Visitor<R> visitor);

        interface Visitor<R> {

            R visit(MemberRole.Draft draft);
        }
    }
}
