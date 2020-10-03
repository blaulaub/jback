package ch.patchcode.jback.securityEntities;

import ch.patchcode.jback.secBase.secModelImpl.Authority;
import ch.patchcode.jback.util.WithFirstAndLastName;

import java.util.List;

public interface Principal extends
        java.security.Principal,
        ch.patchcode.jback.secBase.secModelImpl.Principal<VerificationMean>,
        WithFirstAndLastName {

    // impl ch.patchcode.jback.util.WithFirstAndLastName

    @Override
    String getFirstName();

    @Override
    String getLastName();

    // impl java.security.Principal

    @Override
    String getName();


    // impl ch.patchcode.jback.secBase.secModelImpl.Principal

    @Override
    List<VerificationMean> getMeans();

    @Override
    List<Authority> getBasicPrivileges();
}
