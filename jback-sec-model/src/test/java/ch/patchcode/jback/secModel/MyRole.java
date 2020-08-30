package ch.patchcode.jback.secModel;

import java.util.List;

public class MyRole implements Role<MyOrganisation, MyPerson, MyPrincipal, MyPrivilege, MyRole, MyUser> {

    @Override
    public MyPerson getPerson() {

        return null;
    }

    @Override
    public MyOrganisation getOrganisation() {

        return null;
    }

    @Override
    public List<MyPrivilege> getPrivileges() {

        return null;
    }
}
