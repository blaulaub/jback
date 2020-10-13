package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secModel.AuthenticationMean;
import ch.patchcode.jback.secModel.Organisation;
import ch.patchcode.jback.secModel.Person;

import java.util.List;

public interface Role<TOrganisation extends Organisation, TPerson extends Person, TAuthenticationMean extends AuthenticationMean> extends ch.patchcode.jback.secModel.Role<
        TOrganisation,
        TPerson,
        Principal<TPerson, TAuthenticationMean>,
        TAuthenticationMean,
        Authority,
        Role<TOrganisation, TPerson, TAuthenticationMean>,
        User<TOrganisation, TPerson, TAuthenticationMean>>{

    // from secModel.Principal

    @Override
    TPerson getPerson();

    @Override
    List<Authority> getPrivileges();
}
