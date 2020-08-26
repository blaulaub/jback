package ch.patchcode.jback.core.registration.impl;

import ch.patchcode.jback.core.registration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final Random RND = new Random();

    private final ConsoleVerificationService consoleVerificationService;
    private final EmailVerificationService emailVerificationService;
    private final SmsVerificationService smsVerificationService;
    private final PendingRegistrationRepository pendingRegistrationRepository;

    @Autowired
    public RegistrationServiceImpl(
            ConsoleVerificationService consoleVerificationService,
            EmailVerificationService emailVerificationService,
            SmsVerificationService smsVerificationService,
            PendingRegistrationRepository pendingRegistrationRepository
    ) {
        this.consoleVerificationService = consoleVerificationService;
        this.emailVerificationService = emailVerificationService;
        this.smsVerificationService = smsVerificationService;
        this.pendingRegistrationRepository = pendingRegistrationRepository;
    }

    @Override
    public PendingRegistration.Id process(InitialRegistrationData data) {

        var pendingRegistration = new PendingRegistration.Builder()
                .setFirstName(data.getFirstName())
                .setLastName(data.getLastName())
                .setVerificationMean(data.getVerificationMean())
                .setVerificationCode(String.format("%04d", RND.nextInt(10000)))
                .setExpiresAt(Instant.now().plus(Duration.ofMinutes(30)))
                .build();

        var verificationMeanVisitor = new MyVerificationMeanVisitor(pendingRegistration);
        verificationMeanVisitor.visit();

        return pendingRegistrationRepository.save(pendingRegistration);
    }

    @Override
    public ConfirmationResult confirm(UUID id, String verificationCode) {

        throw new RuntimeException("not implemented");
    }

    private class MyVerificationMeanVisitor implements VerificationMean.VerificationMeanVisitor<Void> {

        private final PendingRegistration pendingRegistration;

        public MyVerificationMeanVisitor(PendingRegistration pendingRegistration) {
            this.pendingRegistration = pendingRegistration;
        }

        public void visit() {
            pendingRegistration.getVerificationMean().accept(this);
        }

        @Override
        public Void visit(VerificationMean.VerificationByConsole verificationByConsole) {
            consoleVerificationService.sendOut(pendingRegistration);
            return null;
        }

        @Override
        public Void visit(VerificationMean.VerificationByEmail verificationByEmail) {
            emailVerificationService.sendOut(pendingRegistration);
            return null;
        }

        @Override
        public Void visit(VerificationMean.VerificationBySms verificationBySms) {
            smsVerificationService.sendOut(pendingRegistration);
            return null;
        }
    }
}
