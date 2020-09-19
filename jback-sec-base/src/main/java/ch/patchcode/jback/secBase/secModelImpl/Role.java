package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secBase.VerificationMean;

import java.util.List;

public interface Role<TVerificationMean extends VerificationMean> extends ch.patchcode.jback.secModel.Role<
        Organisation,
        Person<TVerificationMean>,
        Principal<TVerificationMean>,
        Authority,
        Role<TVerificationMean>,
        User<TVerificationMean>>{

    // from secModel.Principal

    @Override
    Person<TVerificationMean> getPerson();

    @Override
    List<Authority> getPrivileges();
}
