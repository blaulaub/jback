package ch.patchcode.jback.secModel;

public class MyUser implements User<MyOrganisation, MyPerson, MyPrincipal, MyPrivilege, MyRole, MyUser> {

    @Override
    public MyPrincipal getPrincipal() {

        return null;
    }

    @Override
    public MyRole getRole() {

        return null;
    }
}
