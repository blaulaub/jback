package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secModel.AuthenticationMean;

public interface User<TAuthenticationMean extends AuthenticationMean> extends ch.patchcode.jback.secModel.User<
        Organisation,
        Person,
        Principal<TAuthenticationMean>,
        TAuthenticationMean,
        Authority,
        Role<TAuthenticationMean>,
        User<TAuthenticationMean>> {

    // from secModel.User

    @Override
    Principal<TAuthenticationMean> getPrincipal();

    @Override
    Role<TAuthenticationMean> getRole();
}
