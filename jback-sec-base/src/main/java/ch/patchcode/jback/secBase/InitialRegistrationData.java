package ch.patchcode.jback.secBase;

/**
 * This is the record expected from people that want to register.
 * They have to provide their name, and some contact media.
 */
public interface InitialRegistrationData {

    String getFirstName();

    String getLastName();

    VerificationMean.Draft getVerificationMean();
}
