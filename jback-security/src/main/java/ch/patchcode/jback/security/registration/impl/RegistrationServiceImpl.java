package ch.patchcode.jback.security.registration.impl;

import ch.patchcode.jback.secBase.ConfirmationResult;
import ch.patchcode.jback.security.registration.PendingRegistrationRepository;
import ch.patchcode.jback.security.registration.RegistrationService;
import ch.patchcode.jback.security.registration.VerificationService.ConsoleVerificationService;
import ch.patchcode.jback.security.registration.VerificationService.EmailVerificationService;
import ch.patchcode.jback.security.registration.VerificationService.SmsVerificationService;
import ch.patchcode.jback.security.secBaseImpl.InitialRegistrationData;
import ch.patchcode.jback.security.secBaseImpl.PendingRegistration;
import ch.patchcode.jback.security.secBaseImpl.VerificationMean;
import ch.patchcode.jback.security.verificationCodes.VerificationCodeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
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

    private class RegistrationSender implements VerificationMean.Visitor<Void> {

        private final PendingRegistration pendingRegistration;

        public RegistrationSender(PendingRegistration pendingRegistration) {

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
