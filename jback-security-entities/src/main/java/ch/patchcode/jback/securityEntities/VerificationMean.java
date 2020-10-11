package ch.patchcode.jback.securityEntities;

import java.util.UUID;

public interface VerificationMean extends ch.patchcode.jback.secBase.VerificationMean {

    UUID getId();

    <R> R accept(Visitor<R> registrationHandler);

    Draft toNewDraft();

    interface Draft extends ch.patchcode.jback.secBase.VerificationMean.Draft {

        <R> R accept(Visitor<R> visitor);

        interface Visitor<R> {

            R visit(VerificationByConsole.Draft draft);

            R visit(VerificationByEmail.Draft draft);

            R visit(VerificationBySms.Draft draft);

            R visit(VerificationByPassword.Draft draft);
        }
    }

    interface Visitor<R> {

        R visit(VerificationByConsole verificationByConsole);

        R visit(VerificationByEmail verificationByEmail);

        R visit(VerificationBySms verificationBySms);

        R visit(VerificationByPassword verificationByPassword);
    }
}
