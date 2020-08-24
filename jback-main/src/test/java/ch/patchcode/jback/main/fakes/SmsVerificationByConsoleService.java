package ch.patchcode.jback.main.fakes;

import ch.patchcode.jback.core.registration.ConsoleVerificationService;
import ch.patchcode.jback.core.registration.PendingRegistration;
import ch.patchcode.jback.core.registration.SmsVerificationService;
import org.springframework.stereotype.Service;

@Service
public class SmsVerificationByConsoleService implements SmsVerificationService {

    private final ConsoleVerificationService consoleVerificationService;

    public SmsVerificationByConsoleService(ConsoleVerificationService consoleVerificationService) {
        this.consoleVerificationService = consoleVerificationService;
    }

    @Override
    public void sendOut(PendingRegistration pendingRegistration) {
        consoleVerificationService.sendOut(pendingRegistration);
    }
}
