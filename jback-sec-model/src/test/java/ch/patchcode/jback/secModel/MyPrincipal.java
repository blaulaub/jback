package ch.patchcode.jback.secModel;

import java.util.List;

public class MyPrincipal implements Principal<MyOrganisation, MyPerson, MyPrincipal, MyPrivilege, MyRole, MyUser> {

    @Override
    public List<MyPrivilege> getBasicPrivileges() {

        return null;
    }
}
