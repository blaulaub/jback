package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secModel.AuthenticationMean;
import ch.patchcode.jback.secModel.Person;

import java.util.List;

public interface Principal<TPerson extends Person, TAuthenticationMean extends AuthenticationMean>
        extends ch.patchcode.jback.secModel.Principal<TPerson, Authority, TAuthenticationMean> {

    // from secModel.Principal

    @Override
    List<TAuthenticationMean> getMeans();

    @Override
    List<Authority> getBasicPrivileges();
}
