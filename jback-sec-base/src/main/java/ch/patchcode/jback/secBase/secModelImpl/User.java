package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secBase.VerificationMean;

public interface User<TVerificationMean extends VerificationMean> extends ch.patchcode.jback.secModel.User<
        Organisation,
        Person<TVerificationMean>,
        Principal<TVerificationMean>,
        Authority,
        Role<TVerificationMean>,
        User<TVerificationMean>> {

    // from secModel.User

    @Override
    Principal<TVerificationMean> getPrincipal();

    @Override
    Role<TVerificationMean> getRole();
}
