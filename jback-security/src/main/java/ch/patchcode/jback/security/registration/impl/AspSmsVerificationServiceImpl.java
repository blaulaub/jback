package ch.patchcode.jback.security.registration.impl;

import ch.patchcode.jback.security.registration.VerificationService;
import ch.patchcode.jback.securityEntities.PendingRegistration;
import ch.patchcode.jback.securityEntities.VerificationMean;

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
            public String visit(VerificationMean.VerificationByConsole verificationByConsole) {
                throw new IllegalArgumentException();
            }

            @Override
            public String visit(VerificationMean.VerificationByEmail verificationByEmail) {
                throw new IllegalArgumentException();
            }

            @Override
            public String visit(VerificationMean.VerificationBySms verificationBySms) {
                return verificationBySms.getPhoneNumber();
            }
        });
    }

    public interface AspSmsJsonApi {

        void SendSimpleTextSMS(String phoneNumber, String text);
    }
}
