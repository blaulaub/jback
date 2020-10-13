package ch.patchcode.jback.coreEntities;

import ch.patchcode.jback.secModel.Organisation;
import ch.patchcode.jback.secModel.Person;

import java.util.List;

public interface Role<TOrganisation extends Organisation>
        extends ch.patchcode.jback.secModel.Role<TOrganisation, Person, Authority> {

    @Override
    Person getPerson();

    @Override
    List<Authority> getPrivileges();
}
