package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secModel.AuthenticationMean;
import ch.patchcode.jback.secModel.Person;

import java.util.List;

public interface Role<TPerson extends Person, TAuthenticationMean extends AuthenticationMean> extends ch.patchcode.jback.secModel.Role<
        Organisation,
        TPerson,
        Principal<TPerson, TAuthenticationMean>,
        TAuthenticationMean,
        Authority,
        Role<TPerson, TAuthenticationMean>,
        User<TPerson, TAuthenticationMean>>{

    // from secModel.Principal

    @Override
    TPerson getPerson();

    @Override
    List<Authority> getPrivileges();
}
