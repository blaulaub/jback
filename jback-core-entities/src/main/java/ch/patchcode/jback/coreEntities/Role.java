package ch.patchcode.jback.coreEntities;

import ch.patchcode.jback.secModel.Person;

import java.util.List;

public interface Role
        extends ch.patchcode.jback.secModel.Role<Club, Person, Authority> {

    @Override
    Person getPerson();

    @Override
    Club getOrganisation();

    @Override
    List<Authority> getPrivileges();
}
