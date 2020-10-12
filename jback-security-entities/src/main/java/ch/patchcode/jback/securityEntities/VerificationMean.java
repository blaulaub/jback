package ch.patchcode.jback.securityEntities;

import java.util.UUID;

public interface VerificationMean extends ch.patchcode.jback.secBase.VerificationMean {

    UUID getId();

    <R> R accept(Visitor<R> registrationHandler);

    Draft toNewDraft();

    interface Draft extends ch.patchcode.jback.secBase.VerificationMean.Draft {

        <R> R accept(Visitor<R> visitor);

        interface Visitor<R> {

            R visit(VerificationByConsole.Draft consoleDraft);

            R visit(VerificationByEmail.Draft emailDraft);

            R visit(VerificationBySms.Draft smsDraft);

            R visit(VerificationByPassword.Draft passwordDraft);
        }
    }

    interface Visitor<R> {

        R visit(VerificationByConsole verificationByConsole);

        R visit(VerificationByEmail verificationByEmail);

        R visit(VerificationBySms verificationBySms);

        R visit(VerificationByPassword verificationByPassword);
    }
}
