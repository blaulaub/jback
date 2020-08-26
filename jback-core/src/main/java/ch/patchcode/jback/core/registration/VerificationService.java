package ch.patchcode.jback.core.registration;

public interface VerificationService {

    void sendOut(PendingRegistration pendingRegistration);
}
