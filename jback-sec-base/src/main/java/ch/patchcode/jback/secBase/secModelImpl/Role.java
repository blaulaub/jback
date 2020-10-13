package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secModel.Organisation;
import ch.patchcode.jback.secModel.Person;

import java.util.List;

public interface Role<
        TOrganisation extends Organisation,
        TPerson extends Person
        > extends ch.patchcode.jback.secModel.Role<
        TOrganisation,
        TPerson,
        Authority> {

    @Override
    TPerson getPerson();

    @Override
    List<Authority> getPrivileges();
}
