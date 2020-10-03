package ch.patchcode.jback.securityEntities;

public interface VerificationMean extends ch.patchcode.jback.secBase.VerificationMean {

    <R> R accept(Visitor<R> registrationHandler);

    Draft toNewDraft();

    interface Draft {

        <R> R accept(Visitor<R> visitor);

        interface Visitor<R> {

            R visit(VerificationByConsole.Draft draft);

            R visit(VerificationByEmail.Draft draft);

            R visit(VerificationBySms.Draft draft);

            R visit(VerificationByUsernameAndPassword.Draft draft);
        }
    }

    interface Visitor<R> {

        R visit(VerificationByConsole verificationByConsole);

        R visit(VerificationByEmail verificationByEmail);

        R visit(VerificationBySms verificationBySms);

        R visit(VerificationByUsernameAndPassword verificationByUsernameAndPassword);
    }
}
