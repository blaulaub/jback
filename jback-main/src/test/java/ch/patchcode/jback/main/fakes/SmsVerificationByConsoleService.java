package ch.patchcode.jback.main.fakes;

import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.security.registration.VerificationService.SmsVerificationService;
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
