package ch.patchcode.jback.secModel;

public class MyUser implements User<MyOrganisation, MyPerson, MyPrincipal, MyPrivilege, MyRole, MyUser> {

    @Override
    public MyPrincipal getPrincipal() {

        return null;
    }

    @Override
    public MyPerson getPerson() {

        return null;
    }
}
