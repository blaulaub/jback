package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secModel.AuthenticationMean;
import ch.patchcode.jback.secModel.Organisation;
import ch.patchcode.jback.secModel.Person;
import ch.patchcode.jback.secModel.Principal;

import java.util.List;

public interface Role<
        TOrganisation extends Organisation,
        TPerson extends Person,
        TPrincipal extends Principal<TPerson, Authority, TAuthenticationMean>,
        TAuthenticationMean extends AuthenticationMean
        > extends ch.patchcode.jback.secModel.Role<
        TOrganisation,
        TPerson,
        TPrincipal,
        TAuthenticationMean,
        Authority,
        Role<TOrganisation, TPerson, TPrincipal, TAuthenticationMean>,
        User<TOrganisation, TPerson, TPrincipal, TAuthenticationMean>>{

    // from secModel.Principal

    @Override
    TPerson getPerson();

    @Override
    List<Authority> getPrivileges();
}
