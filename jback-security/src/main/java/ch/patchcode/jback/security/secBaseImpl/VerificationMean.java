package ch.patchcode.jback.security.secBaseImpl;

import org.inferred.freebuilder.FreeBuilder;

public interface VerificationMean extends ch.patchcode.jback.secBase.VerificationMean {

    <R> R accept(VerificationMean.VerificationMeanVisitor<R> registrationHandler);

    class VerificationByConsole implements ch.patchcode.jback.secBase.VerificationMean.VerificationByConsole, VerificationMean {

        // boring - there are no parameters :-)

        @Override
        public <R> R accept(VerificationMeanVisitor<R> registrationHandler) {

            return registrationHandler.visit(this);
        }
    }

    @FreeBuilder
    abstract class VerificationByEmail implements ch.patchcode.jback.secBase.VerificationMean.VerificationByEmail, VerificationMean {

        public abstract String getEmailAddress();

        @Override
        public <R> R accept(VerificationMeanVisitor<R> registrationHandler) {

            return registrationHandler.visit(this);
        }

        public static class Builder extends VerificationMean_VerificationByEmail_Builder {}
    }

    @FreeBuilder
    abstract class VerificationBySms implements ch.patchcode.jback.secBase.VerificationMean.VerificationBySms, VerificationMean {

        public abstract String getPhoneNumber();

        @Override
        public <R> R accept(VerificationMeanVisitor<R> registrationHandler) {

            return registrationHandler.visit(this);
        }

        public static class Builder extends VerificationMean_VerificationBySms_Builder {}
    }

    interface VerificationMeanVisitor<R> {

        R visit(VerificationMean.VerificationByConsole verificationByConsole);

        R visit(VerificationMean.VerificationByEmail verificationByEmail);

        R visit(VerificationMean.VerificationBySms verificationBySms);
    }

}
