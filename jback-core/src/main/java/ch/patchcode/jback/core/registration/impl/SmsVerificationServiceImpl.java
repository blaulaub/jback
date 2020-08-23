package ch.patchcode.jback.core.registration.impl;

import ch.patchcode.jback.core.registration.PendingRegistration;
import ch.patchcode.jback.core.registration.SmsVerificationService;
import org.springframework.stereotype.Service;

@Service
public class SmsVerificationServiceImpl implements SmsVerificationService {

    @Override
    public void sendOut(PendingRegistration pendingRegistration) {

        throw new RuntimeException("not implemented");
    }
}
