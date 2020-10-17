package ch.patchcode.jback.coreEntities.roles;

import ch.patchcode.jback.coreEntities.Authority;
import ch.patchcode.jback.coreEntities.Club;
import ch.patchcode.jback.coreEntities.Person;

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

    void accept(Visitor visitor);

    <T> T accept(ResultVisitor<T> visitor);

    interface Draft {

        Person getPerson();

        Club getOrganisation();

        <R> R accept(Visitor<R> visitor);

        interface Visitor<R> {

            R visit(MemberRole.Draft draft);
        }
    }

    interface Visitor {

        void visit(MemberRole memberRole);
    }

    interface ResultVisitor<T> {

        T visit(MemberRole memberRole);
    }
}
