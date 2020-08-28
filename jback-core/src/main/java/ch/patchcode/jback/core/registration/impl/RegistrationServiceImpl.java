package ch.patchcode.jback.core.registration.impl;

import ch.patchcode.jback.core.registration.*;
import ch.patchcode.jback.core.registration.VerificationService.ConsoleVerificationService;
import ch.patchcode.jback.core.registration.VerificationService.EmailVerificationService;
import ch.patchcode.jback.core.registration.VerificationService.SmsVerificationService;
import ch.patchcode.jback.core.verificationCodes.VerificationCodeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final ConsoleVerificationService consoleVerificationService;
    private final EmailVerificationService emailVerificationService;
    private final SmsVerificationService smsVerificationService;
    private final PendingRegistrationRepository pendingRegistrationRepository;
    private final VerificationCodeProvider verificationCodeProvider;

    @Autowired
    public RegistrationServiceImpl(
            ConsoleVerificationService consoleVerificationService,
            EmailVerificationService emailVerificationService,
            SmsVerificationService smsVerificationService,
            PendingRegistrationRepository pendingRegistrationRepository,
            VerificationCodeProvider verificationCodeProvider
    ) {
        this.consoleVerificationService = consoleVerificationService;
        this.emailVerificationService = emailVerificationService;
        this.smsVerificationService = smsVerificationService;
        this.pendingRegistrationRepository = pendingRegistrationRepository;
        this.verificationCodeProvider = verificationCodeProvider;
    }

    @Override
    public PendingRegistration.Id setupRegistration(InitialRegistrationData data) {

        var pendingRegistration = new PendingRegistration.Builder()
                .setFirstName(data.getFirstName())
                .setLastName(data.getLastName())
                .setVerificationMean(data.getVerificationMean())
                .setVerificationCode(verificationCodeProvider.generateRandomCode())
                .setExpiresAt(Instant.now().plus(Duration.ofMinutes(30)))
                .build();

        var verificationMeanVisitor = new MyVerificationMeanVisitor(pendingRegistration);
        verificationMeanVisitor.visit();

        return pendingRegistrationRepository.save(pendingRegistration);
    }

    @Override
    public ConfirmationResult confirmRegistration(UUID id, String verificationCode) {

        var pendingRegistration = pendingRegistrationRepository.findById(id);

        if (pendingRegistration.isEmpty()) {
            return ConfirmationResult.NOT_FOUND;
        }

        if (pendingRegistration.get().getExpiresAt().isBefore(Instant.now())) {
            return ConfirmationResult.NOT_FOUND;
        }

        if (pendingRegistration.get().getVerificationCode().equals(verificationCode)) {
            return ConfirmationResult.CONFIRMED;
        }

        return ConfirmationResult.MISMATCH;
    }

    @Override
    public void removeRegistration(UUID id) {

        pendingRegistrationRepository.removeById(id);
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
