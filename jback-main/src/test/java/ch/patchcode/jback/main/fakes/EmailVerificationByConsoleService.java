package ch.patchcode.jback.main.fakes;

import ch.patchcode.jback.security.registration.VerificationService.EmailVerificationService;
import ch.patchcode.jback.securityEntities.PendingRegistration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class EmailVerificationByConsoleService implements EmailVerificationService {

    private final ConsoleVerificationService consoleVerificationService;

    public EmailVerificationByConsoleService(ConsoleVerificationService consoleVerificationService) {
        this.consoleVerificationService = consoleVerificationService;
    }

    @Override
    public void sendOut(PendingRegistration pendingRegistration) {
        consoleVerificationService.sendOut(pendingRegistration);
    }
}
