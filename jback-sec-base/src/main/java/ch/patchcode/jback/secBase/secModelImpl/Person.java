package ch.patchcode.jback.secBase.secModelImpl;

import java.util.List;

public interface Person extends ch.patchcode.jback.secModel.Person<Organisation, Person, Principal, Authority, Role, User> {

    // from secModel.Principal

    @Override
    List<Principal> getPrincipals();

    @Override
    List<Role> getRoles();

    @Override
    List<Authority> getExtraPrivileges();
}
