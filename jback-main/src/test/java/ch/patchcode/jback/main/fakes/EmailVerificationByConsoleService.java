package ch.patchcode.jback.main.fakes;

import ch.patchcode.jback.core.registration.ConsoleVerificationService;
import ch.patchcode.jback.core.registration.EmailVerificationService;
import ch.patchcode.jback.core.registration.PendingRegistration;
import org.springframework.stereotype.Service;

@Service
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
