package ch.patchcode.jback.security.registration.impl;

import ch.patchcode.jback.security.registration.VerificationService;
import ch.patchcode.jback.securityEntities.*;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailVerificationServiceImpl implements VerificationService.EmailVerificationService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailVerificationServiceImpl.class);

    private final String smtpServer;
    private final int smtpPort;
    private final String smtpUserName;
    private final String smtpPassword;
    private final String smtpFrom;

    public EmailVerificationServiceImpl(
            String smtpServer,
            int smtpPort,
            String smtpUserName,
            String smtpPassword,
            String smtpFrom
    ) {

        this.smtpServer = smtpServer;
        this.smtpPort = smtpPort;
        this.smtpUserName = smtpUserName;
        this.smtpPassword = smtpPassword;
        this.smtpFrom = smtpFrom;
    }

    @Override
    public void sendOut(PendingRegistration pendingRegistration) {

        String emailAddress = getEmailAddress(pendingRegistration);

        Email email = new SimpleEmail();
        email.setSubject("Verification Code");

        try {
            email.setFrom(smtpFrom);
            email.setMsg("Your verification code is " + pendingRegistration.getVerificationCode());
            email.addTo(emailAddress);
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }

        email.setHostName(smtpServer);
        email.setSmtpPort(smtpPort);
        email.setAuthenticator(new DefaultAuthenticator(smtpUserName, smtpPassword));

        try {
            email.send();
            LOG.info("sent Email to {}", emailAddress);
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }

    private String getEmailAddress(PendingRegistration pendingRegistration) {

        return pendingRegistration.getVerificationMean().accept(new VerificationMean.Draft.Visitor<String>() {

            @Override
            public String visit(VerificationByConsole.Draft verificationByConsole) {
                throw new IllegalArgumentException();
            }

            @Override
            public String visit(VerificationByEmail.Draft verificationByEmail) {
                return verificationByEmail.getEmailAddress();
            }

            @Override
            public String visit(VerificationBySms.Draft verificationBySms) {
                throw new IllegalArgumentException();
            }

            @Override
            public String visit(VerificationByUsernameAndPassword.Draft verificationByUsernameAndPassword) {

                throw new IllegalArgumentException();
            }
        });
    }
}
