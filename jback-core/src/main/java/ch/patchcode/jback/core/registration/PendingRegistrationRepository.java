package ch.patchcode.jback.core.registration;

public interface PendingRegistrationRepository {
    void save(PendingRegistration pendingRegistration);
}
