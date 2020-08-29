package ch.patchcode.jback.secModel;

import java.util.List;

public class MyPerson implements Person<MyOrganisation, MyPerson, MyPrincipal, MyPrivilege, MyRole, MyUser> {

    @Override
    public List<MyRole> getRoles() {

        return null;
    }

    @Override
    public List<MyPrivilege> getExtraPrivileges() {

        return null;
    }
}
