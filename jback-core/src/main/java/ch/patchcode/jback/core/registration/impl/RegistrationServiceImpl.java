package ch.patchcode.jback.core.registration.impl;

import ch.patchcode.jback.core.registration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final Random RND = new Random();

    private final ConsoleVerificationService consoleVerificationService;
    private final EmailVerificationServiceImpl emailVerificationService;
    private final SmsVerificationService smsVerificationService;
    private final PendingRegistrationRepository pendingRegistrationRepository;

    @Autowired
    public RegistrationServiceImpl(
            ConsoleVerificationService consoleVerificationService,
            EmailVerificationServiceImpl emailVerificationService,
            SmsVerificationService smsVerificationService,
            PendingRegistrationRepository pendingRegistrationRepository
    ) {
        this.consoleVerificationService = consoleVerificationService;
        this.emailVerificationService = emailVerificationService;
        this.smsVerificationService = smsVerificationService;
        this.pendingRegistrationRepository = pendingRegistrationRepository;
    }

    @Override
    public void process(InitialRegistrationData data) {

        var pendingRegistration = new PendingRegistration.Builder()
                .setFirstName(data.getFirstName())
                .setLastName(data.getLastName())
                .setVerificationMean(data.getVerificationMean())
                .setVerificationCode(String.format("%04d", RND.nextInt(10000)))
                .setExpiresAt(Instant.now().plus(Duration.ofMinutes(30)))
                .build();

        var verificationMeanVisitor = new MyVerificationMeanVisitor(pendingRegistration);
        verificationMeanVisitor.visit();

        pendingRegistrationRepository.save(pendingRegistration);
    }

    private class MyVerificationMeanVisitor implements VerificationMean.VerificationMeanVisitor {

        private final PendingRegistration pendingRegistration;

        public MyVerificationMeanVisitor(PendingRegistration pendingRegistration) {
            this.pendingRegistration = pendingRegistration;
        }

        public void visit() {
            pendingRegistration.getVerificationMean().accept(this);
        }

        @Override
        public void visit(VerificationMean.VerificationByConsole verificationByConsole) {
            consoleVerificationService.sendOut(pendingRegistration);
        }

        @Override
        public void visit(VerificationMean.VerificationByEmail verificationByEmail) {
            emailVerificationService.sendOut(pendingRegistration);
        }

        @Override
        public void visit(VerificationMean.VerificationBySms verificationBySms) {
            smsVerificationService.sendOut(pendingRegistration);
        }
    }
}
