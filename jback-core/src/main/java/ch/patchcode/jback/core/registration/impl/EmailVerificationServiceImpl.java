package ch.patchcode.jback.core.registration.impl;

import ch.patchcode.jback.core.registration.EmailVerificationService;
import ch.patchcode.jback.core.registration.PendingRegistration;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    @Override
    public void sendOut(PendingRegistration pendingRegistration) {

        throw new RuntimeException("not implemented");
    }
}
