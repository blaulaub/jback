package ch.patchcode.jback.secBase;

public interface AuthorizationManager {

    PendingRegistration.Id setupRegistration(InitialRegistrationData initialRegistrationData);

    void authenticate(PendingRegistration.Id registrationId, VerificationCode verificationCode);
}
