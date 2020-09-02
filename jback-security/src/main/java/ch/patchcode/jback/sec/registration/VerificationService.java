package ch.patchcode.jback.sec.registration;

public interface VerificationService {

    void sendOut(PendingRegistration pendingRegistration);

    // can eventually be moved to separate file/package
    interface ConsoleVerificationService extends VerificationService {
    }

    // can eventually be moved to separate file/package
    interface EmailVerificationService extends VerificationService {
    }

    // can eventually be moved to separate file/package
    interface SmsVerificationService extends VerificationService {
    }
}
