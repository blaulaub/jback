package ch.patchcode.jback.security.registration;

import ch.patchcode.jback.securityEntities.registration.PendingRegistration;

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
