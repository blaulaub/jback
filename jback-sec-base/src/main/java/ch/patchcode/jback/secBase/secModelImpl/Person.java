package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secBase.VerificationMean;

import java.util.List;

public interface Person<TVerificationMean extends VerificationMean> extends ch.patchcode.jback.secModel.Person<
        Organisation,
        Person<TVerificationMean>,
        Principal<TVerificationMean>,
        Authority,
        Role<TVerificationMean>,
        User<TVerificationMean>> {

    // from secModel.Principal

    @Override
    List<Principal<TVerificationMean>> getPrincipals();
}
