package ch.patchcode.jback.core.registration;

import org.inferred.freebuilder.FreeBuilder;

public interface VerificationMean {

    void accept(VerificationMeanVisitor registrationHandler);

    class VerificationByConsole implements VerificationMean {

        // boring - there are no parameters :-)

        @Override
        public void accept(VerificationMeanVisitor registrationHandler) {
            registrationHandler.visit(this);
        }
    }

    @FreeBuilder
    abstract class VerificationByEmail implements VerificationMean {

        public abstract String getEmailAddress();

        @Override
        public void accept(VerificationMeanVisitor registrationHandler) {
            registrationHandler.visit(this);
        }

        public static class Builder extends VerificationMean_VerificationByEmail_Builder {}
    }

    @FreeBuilder
    abstract class VerificationBySms implements VerificationMean {

        public abstract String getPhoneNumber();

        @Override
        public void accept(VerificationMeanVisitor registrationHandler) {
            registrationHandler.visit(this);
        }

        public static class Builder extends VerificationMean_VerificationBySms_Builder {}
    }

    interface VerificationMeanVisitor {

        void visit(VerificationByConsole verificationByConsole);

        void visit(VerificationByEmail verificationByEmail);

        void visit(VerificationBySms verificationBySms);
    }
}
