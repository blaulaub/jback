package ch.patchcode.jback.security.registration.impl;

import ch.patchcode.jback.security.registration.VerificationService.ConsoleVerificationService;
import ch.patchcode.jback.security.entities.PendingRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleVerificationServiceImpl implements ConsoleVerificationService {

    private final static Logger LOG = LoggerFactory.getLogger(ConsoleVerificationServiceImpl.class);

    @Override
    public void sendOut(PendingRegistration pendingRegistration) {

        LOG.info("Dear {} {}, your verification code is {}",
                pendingRegistration.getFirstName(),
                pendingRegistration.getLastName(),
                pendingRegistration.getVerificationCode()
        );
    }
}
