package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secBase.VerificationMean;

import java.util.List;

public interface Principal<TVerificationMean extends VerificationMean> extends ch.patchcode.jback.secModel.Principal<Person, Authority> {

    /**
     * List of means the principal can be verified by.
     */
    List<TVerificationMean> getMeans();

    // from secModel.Principal

    @Override
    List<Authority> getBasicPrivileges();
}
