package ch.patchcode.jback.security.registration.impl;

import ch.patchcode.jback.security.registration.VerificationService;
import ch.patchcode.jback.securityEntities.*;

public class AspSmsVerificationServiceImpl implements VerificationService.SmsVerificationService {

    private final AspSmsJsonApi aspSmsJsonApi;

    public AspSmsVerificationServiceImpl(AspSmsJsonApi aspSmsJsonApi) {
        this.aspSmsJsonApi = aspSmsJsonApi;
    }

    @Override
    public void sendOut(PendingRegistration pendingRegistration) {

        aspSmsJsonApi.SendSimpleTextSMS(
                getPhoneNumber(pendingRegistration),
                getSmsText(pendingRegistration)
        );
    }

    private String getSmsText(PendingRegistration pendingRegistration) {
        return "Your verification code is " + pendingRegistration.getVerificationCode();
    }

    private String getPhoneNumber(PendingRegistration pendingRegistration) {
        return pendingRegistration.getVerificationMean().accept(new VerificationMean.Visitor<String>() {

            @Override
            public String visit(VerificationByConsole verificationByConsole) {
                throw new IllegalArgumentException();
            }

            @Override
            public String visit(VerificationByEmail verificationByEmail) {
                throw new IllegalArgumentException();
            }

            @Override
            public String visit(VerificationBySms verificationBySms) {
                return verificationBySms.getPhoneNumber();
            }

            @Override
            public String visit(VerificationByUsernameAndPassword verificationByUsernameAndPassword) {
                throw new IllegalArgumentException();
            }
        });
    }

    public interface AspSmsJsonApi {

        void SendSimpleTextSMS(String phoneNumber, String text);
    }
}
