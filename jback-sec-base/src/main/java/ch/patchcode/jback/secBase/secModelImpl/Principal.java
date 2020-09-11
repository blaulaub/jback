package ch.patchcode.jback.secBase.secModelImpl;

import ch.patchcode.jback.secBase.VerificationMean;

import java.util.List;

public interface Principal extends ch.patchcode.jback.secModel.Principal<Authority> {

    /**
     * List of means the principal can be verified by.
     */
    List<VerificationMean> getMeans();

    // from secModel.Principal

    @Override
    List<Authority> getBasicPrivileges();
}
