package ch.patchcode.jback.secBase;

import ch.patchcode.jback.secBase.secModelImpl.Person;
import ch.patchcode.jback.secBase.secModelImpl.Principal;

public interface AuthorizationManager<
        TPerson extends Person<TVerificationMean>,
        TInitialRegistrationData extends InitialRegistrationData,
        TVerificationMean extends VerificationMean> {

    /**
     * Make the person a client of the given principal.
     *
     * @param principal
     * @param person
     */
    void addClient(Principal<TVerificationMean> principal, TPerson person);
}
