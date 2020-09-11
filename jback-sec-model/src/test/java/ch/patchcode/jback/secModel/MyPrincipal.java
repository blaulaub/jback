package ch.patchcode.jback.secModel;

import java.util.List;

public class MyPrincipal implements Principal<MyPrivilege> {

    @Override
    public List<MyPrivilege> getBasicPrivileges() {

        return null;
    }
}
