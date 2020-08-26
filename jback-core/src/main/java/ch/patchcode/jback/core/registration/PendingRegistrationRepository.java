package ch.patchcode.jback.core.registration;

public interface PendingRegistrationRepository {

    PendingRegistration.Id save(PendingRegistration pendingRegistration);
}
