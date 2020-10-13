package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secModel.AuthenticationMean;
import ch.patchcode.jback.secModel.Person;

public interface User<TPerson extends Person, TAuthenticationMean extends AuthenticationMean> extends ch.patchcode.jback.secModel.User<
        Organisation,
        TPerson,
        Principal<TPerson, TAuthenticationMean>,
        TAuthenticationMean,
        Authority,
        Role<TPerson, TAuthenticationMean>,
        User<TPerson, TAuthenticationMean>> {

    // from secModel.User

    @Override
    Principal<TPerson, TAuthenticationMean> getPrincipal();

    @Override
    Role<TPerson, TAuthenticationMean> getRole();
}
