package ch.patchcode.jback.securityEntities;

import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.secBase.secModelImpl.Authority;

import java.util.List;

public interface Principal extends
        java.security.Principal,
        ch.patchcode.jback.secBase.secModelImpl.Principal<Person, VerificationMean>  {

    String getFirstName();

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
