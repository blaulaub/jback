package ch.patchcode.jback.secBase.secModelImpl;

public interface User extends ch.patchcode.jback.secModel.User<Organisation, Person, Principal, Authority, Role, User> {

    // from secModel.Principal

    @Override
    Principal getPrincipal();

    @Override
    Role getRole();
}
