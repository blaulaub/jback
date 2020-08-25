package ch.patchcode.jback.core.registration;

public interface RegistrationService {

    PendingRegistration.Id process(InitialRegistrationData toDomain);
}
