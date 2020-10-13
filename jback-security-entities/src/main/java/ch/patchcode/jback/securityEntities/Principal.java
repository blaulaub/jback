package ch.patchcode.jback.securityEntities;

import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.coreEntities.Authority;

import java.util.List;

public interface Principal extends
        java.security.Principal,
        ch.patchcode.jback.secModel.Principal<Person, Authority, VerificationMean>  {

    String getFirstName();

    String getLastName();

    // impl java.security.Principal

    @Override
    String getName();

    @Override
    List<VerificationMean> getMeans();

    @Override
    List<Authority> getBasicPrivileges();
}
