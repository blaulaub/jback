package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secBase.VerificationMean;

import java.util.List;

public interface Role<TVerificationMean extends VerificationMean> extends ch.patchcode.jback.secModel.Role<
        Organisation,
        Person,
        Principal<TVerificationMean>,
        Authority,
        Role<TVerificationMean>,
        User<TVerificationMean>>{

    // from secModel.Principal

    @Override
    Person getPerson();

    @Override
    List<Authority> getPrivileges();
}
