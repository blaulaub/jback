package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secModel.AuthenticationMean;
import ch.patchcode.jback.secModel.Organisation;
import ch.patchcode.jback.secModel.Person;

public interface User<TOrganisation extends Organisation, TPerson extends Person, TAuthenticationMean extends AuthenticationMean> extends ch.patchcode.jback.secModel.User<
        TOrganisation,
        TPerson,
        Principal<TPerson, TAuthenticationMean>,
        TAuthenticationMean,
        Authority,
        Role<TOrganisation, TPerson, TAuthenticationMean>,
        User<TOrganisation, TPerson, TAuthenticationMean>> {

    // from secModel.User

    @Override
    Principal<TPerson, TAuthenticationMean> getPrincipal();

    @Override
    Role<TOrganisation, TPerson, TAuthenticationMean> getRole();
}
