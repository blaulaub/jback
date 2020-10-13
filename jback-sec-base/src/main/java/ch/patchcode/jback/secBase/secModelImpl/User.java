package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secModel.AuthenticationMean;
import ch.patchcode.jback.secModel.Organisation;
import ch.patchcode.jback.secModel.Person;
import ch.patchcode.jback.secModel.Principal;

public interface User<
        TOrganisation extends Organisation,
        TPerson extends Person,
        TPrincipal extends Principal<TPerson, Authority, TAuthenticationMean>,
        TAuthenticationMean extends AuthenticationMean
        > extends ch.patchcode.jback.secModel.User<
        TOrganisation,
        TPerson,
        TPrincipal,
        TAuthenticationMean,
        Authority,
        Role<TOrganisation, TPerson>,
        User<TOrganisation, TPerson, TPrincipal, TAuthenticationMean>> {

    // from secModel.User

    @Override
    TPrincipal getPrincipal();

    @Override
    Role<TOrganisation, TPerson> getRole();
}
