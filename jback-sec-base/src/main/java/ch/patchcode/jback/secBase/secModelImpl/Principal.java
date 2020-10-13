package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secModel.AuthenticationMean;

import java.util.List;

public interface Principal<TAuthenticationMean extends AuthenticationMean>
        extends ch.patchcode.jback.secModel.Principal<Person, Authority, TAuthenticationMean> {

    // from secModel.Principal

    @Override
    List<TAuthenticationMean> getMeans();

    @Override
    List<Authority> getBasicPrivileges();
}
