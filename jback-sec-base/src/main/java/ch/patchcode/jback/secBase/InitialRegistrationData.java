package ch.patchcode.jback.secBase;

import ch.patchcode.jback.util.WithFirstAndLastName;

/**
 * This is the record expected from people that want to register.
 * They have to provide their name, and some contact media.
 */
public interface InitialRegistrationData extends WithFirstAndLastName {

    @Override
    String getFirstName();

    @Override
    String getLastName();

    VerificationMean getVerificationMean();
}
