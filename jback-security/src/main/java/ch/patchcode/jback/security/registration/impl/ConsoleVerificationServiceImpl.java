package ch.patchcode.jback.security.registration.impl;

import ch.patchcode.jback.security.registration.PendingRegistration;
import ch.patchcode.jback.security.registration.VerificationService.ConsoleVerificationService;
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
