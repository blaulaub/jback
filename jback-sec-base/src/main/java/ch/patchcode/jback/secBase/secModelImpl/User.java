package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secModel.*;

public interface User<
        TOrganisation extends Organisation,
        TPerson extends Person,
        TPrincipal extends Principal<TPerson, TPrivilege, TAuthenticationMean>,
        TAuthenticationMean extends AuthenticationMean,
        TPrivilege extends Privilege,
        TRole extends Role<TOrganisation, TPerson, TPrivilege>
        > extends ch.patchcode.jback.secModel.User<
        TOrganisation,
        TPerson,
        TPrincipal,
        TAuthenticationMean,
        TPrivilege,
        TRole,
        User<TOrganisation, TPerson, TPrincipal, TAuthenticationMean, TPrivilege, TRole>> {

    // from secModel.User

    @Override
    TPrincipal getPrincipal();

    @Override
    TRole getRole();
}
