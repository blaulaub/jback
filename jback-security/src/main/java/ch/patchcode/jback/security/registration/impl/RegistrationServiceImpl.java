package ch.patchcode.jback.security.registration.impl;

import ch.patchcode.jback.security.registration.ConfirmationResult;
import ch.patchcode.jback.security.registration.RegistrationService;
import ch.patchcode.jback.security.registration.VerificationService.ConsoleVerificationService;
import ch.patchcode.jback.security.registration.VerificationService.EmailVerificationService;
import ch.patchcode.jback.security.registration.VerificationService.SmsVerificationService;
import ch.patchcode.jback.security.registration.InitialRegistrationData;
import ch.patchcode.jback.security.verificationCodes.VerificationCodeProvider;
import ch.patchcode.jback.securityEntities.registration.PendingRegistration;
import ch.patchcode.jback.securityEntities.registration.PendingRegistrationRepository;
import ch.patchcode.jback.securityEntities.verificationMeans.*;

import javax.inject.Inject;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class RegistrationServiceImpl implements RegistrationService {

    private final ConsoleVerificationService consoleVerificationService;
    private final EmailVerificationService emailVerificationService;
    private final SmsVerificationService smsVerificationService;
    private final PendingRegistrationRepository pendingRegistrationRepository;
    private final VerificationCodeProvider verificationCodeProvider;

    @Inject
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
    public UUID setupRegistration(InitialRegistrationData data) {

        var draft = new PendingRegistration.Draft.Builder()
                .setFirstName(data.getFirstName())
                .setLastName(data.getLastName())
                .setVerificationMean(data.getVerificationMean())
                .setVerificationCode(verificationCodeProvider.generateRandomCode())
                .setExpiresAt(Instant.now().plus(Duration.ofMinutes(30)))
                .build();

        PendingRegistration registration = pendingRegistrationRepository.create(draft);

        var verificationMeanVisitor = new RegistrationSender(registration);
        verificationMeanVisitor.visit();

        return registration.getId();
    }

    @Override
    public Optional<PendingRegistration> getRegistration(UUID id) {

        return pendingRegistrationRepository.findById(id);
    }

    @Override
    public ConfirmationResult confirmRegistration(UUID id, String verificationCode) {

        return getRegistration(id)
                .map(it -> confirmRegistration(it, verificationCode))
                .orElse(ConfirmationResult.NOT_FOUND);
    }

    @Override
    public ConfirmationResult confirmRegistration(PendingRegistration pendingRegistration, String verificationCode) {

        if (pendingRegistration.getExpiresAt().isBefore(Instant.now())) {
            return ConfirmationResult.NOT_FOUND;
        }

        if (pendingRegistration.getVerificationCode().equals(verificationCode)) {
            return ConfirmationResult.CONFIRMED;
        }

        return ConfirmationResult.MISMATCH;
    }

    @Override
    public void removeRegistration(UUID id) {

        pendingRegistrationRepository.removeById(id);
    }

    private class RegistrationSender implements VerificationMean.Draft.Visitor<Void> {

        private final PendingRegistration pendingRegistration;

        public RegistrationSender(PendingRegistration pendingRegistration) {

            this.pendingRegistration = pendingRegistration;
        }

        public void visit() {
            pendingRegistration.getVerificationMean().accept(this);
        }

        @Override
        public Void visit(VerificationByConsole.Draft verificationByConsole) {

            consoleVerificationService.sendOut(pendingRegistration);
            return null;
        }

        @Override
        public Void visit(VerificationByEmail.Draft verificationByEmail) {

            emailVerificationService.sendOut(pendingRegistration);
            return null;
        }

        @Override
        public Void visit(VerificationBySms.Draft verificationBySms) {

            smsVerificationService.sendOut(pendingRegistration);
            return null;
        }

        @Override
        public Void visit(VerificationByPassword.Draft verificationByUsernameAndPassword) {

            // if there is username and password, it does not make sense to send out a verification code
            throw new IllegalArgumentException();
        }
    }
}
