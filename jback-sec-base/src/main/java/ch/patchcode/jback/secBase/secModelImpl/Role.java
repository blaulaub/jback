package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secBase.Perspective;

import java.util.List;

public interface Role extends ch.patchcode.jback.secModel.Role<Organisation, Person, Principal, Authority, Role, User>{

    Perspective getPerspective();

    // from secModel.Principal

    @Override
    Person getPerson();

    @Override
    List<Authority> getPrivileges();
}
