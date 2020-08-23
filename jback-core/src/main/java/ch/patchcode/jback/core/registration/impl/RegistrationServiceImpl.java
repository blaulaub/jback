package ch.patchcode.jback.core.registration.impl;

import ch.patchcode.jback.core.registration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Random;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final Random RND = new Random();

    private final PendingRegistrationRepository pendingRegistrationRepository;

    @Autowired
    public RegistrationServiceImpl(PendingRegistrationRepository pendingRegistrationRepository) {
        this.pendingRegistrationRepository = pendingRegistrationRepository;
    }

    @Override
    public void process(InitialRegistrationData data) {

        var pendingRegistration = new PendingRegistration.Builder()
                .setFirstName(data.getFirstName())
                .setLastName(data.getLastName())
                .setVerificationMean(data.getVerificationMean())
                .setVerificationCode(String.format("%04d",RND.nextInt(10000)))
                .setExpiresAt(Instant.now().plus(Duration.ofMinutes(30)))
                .build();

        var verificationMeanVisitor = new MyVerificationMeanVisitor(pendingRegistration);
        verificationMeanVisitor.visit();

        pendingRegistrationRepository.save(pendingRegistration);
    }

    private class MyVerificationMeanVisitor implements VerificationMean.VerificationMeanVisitor {

        private final PendingRegistration data;

        public MyVerificationMeanVisitor(PendingRegistration data) {
            this.data = data;
        }

        public void visit() {
            data.getVerificationMean().accept(this);
        }

        @Override
        public void visit(VerificationMean.VerificationByConsole verificationByConsole) {
            // - send the user some key to trigger the confirmation step
            // - store the pending request (with timeout)
            throw new RuntimeException("not implemented");
        }

        @Override
        public void visit(VerificationMean.VerificationByEmail verificationByEmail) {
            // - send the user some key to trigger the confirmation step
            // - store the pending request (with timeout)
            throw new RuntimeException("not implemented");
        }

        @Override
        public void visit(VerificationMean.VerificationBySms verificationBySms) {
            // - send the user some key to trigger the confirmation step
            // - store the pending request (with timeout)
            throw new RuntimeException("not implemented");
        }
    }
}
