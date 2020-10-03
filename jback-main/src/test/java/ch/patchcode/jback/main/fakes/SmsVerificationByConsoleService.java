package ch.patchcode.jback.main.fakes;

import ch.patchcode.jback.security.registration.VerificationService.SmsVerificationService;
import ch.patchcode.jback.securityEntities.PendingRegistration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
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
