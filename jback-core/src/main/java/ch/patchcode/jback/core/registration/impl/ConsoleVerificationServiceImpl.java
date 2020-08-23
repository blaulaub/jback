package ch.patchcode.jback.core.registration.impl;

import ch.patchcode.jback.core.registration.ConsoleVerificationService;
import ch.patchcode.jback.core.registration.PendingRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
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
