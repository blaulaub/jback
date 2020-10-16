package ch.patchcode.jback.security.registration.impl;

import ch.patchcode.jback.security.registration.VerificationService;
import ch.patchcode.jback.securityEntities.registration.PendingRegistration;
import ch.patchcode.jback.securityEntities.verificationMeans.*;

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
        return pendingRegistration.getVerificationMean().accept(new VerificationMean.Draft.Visitor<String>() {

            @Override
            public String visit(VerificationByConsole.Draft verificationByConsole) {
                throw new IllegalArgumentException();
            }

            @Override
            public String visit(VerificationByEmail.Draft verificationByEmail) {
                throw new IllegalArgumentException();
            }

            @Override
            public String visit(VerificationBySms.Draft verificationBySms) {
                return verificationBySms.getPhoneNumber();
            }

            @Override
            public String visit(VerificationByPassword.Draft verificationByUsernameAndPassword) {
                throw new IllegalArgumentException();
            }
        });
    }

    public interface AspSmsJsonApi {

        void SendSimpleTextSMS(String phoneNumber, String text);
    }
}
