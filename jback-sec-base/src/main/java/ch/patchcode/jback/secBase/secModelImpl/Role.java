package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secModel.AuthenticationMean;

import java.util.List;

public interface Role<TAuthenticationMean extends AuthenticationMean> extends ch.patchcode.jback.secModel.Role<
        Organisation,
        Person,
        Principal<TAuthenticationMean>,
        TAuthenticationMean,
        Authority,
        Role<TAuthenticationMean>,
        User<TAuthenticationMean>>{

    // from secModel.Principal

    @Override
    Person getPerson();

    @Override
    List<Authority> getPrivileges();
}
